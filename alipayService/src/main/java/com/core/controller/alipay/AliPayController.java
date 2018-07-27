package com.core.controller.alipay;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.core.common.model.AlipayInfo;
import com.core.service.alipay.IAliPayService;
import com.sun.tools.internal.ws.processor.model.Response;

import net.sf.json.JSONObject;

import com.core.config.AliPayConfig;
import com.core.config.GeneralUtil;
import com.core.config.HttpUtil;
import com.core.config.JsonUtil;
import com.core.config.resultStatusInfo.ResultMessage;
import com.core.config.resultStatusInfo.ResultStatus;
import com.core.dao.alipay.AlipayDao;

/**
 * 支付宝接口
 * @author jian.wang
 *
 */
@Controller
public class AliPayController {
	private static final Logger logger = LoggerFactory.getLogger(AliPayController.class);
	@Autowired
	private IAliPayService aliPayService;
	@Autowired
	private AlipayDao alipayDao;
	
	/**
	 * 支付宝退款
	 * @param alipayInfo
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/alipay/refund",method=RequestMethod.POST)
    public String  aliRefund(AlipayInfo alipayInfo,ModelMap map) {
		logger.info("支付宝退款");
		String message  =  aliPayService.aliRefund(alipayInfo);
		map.addAttribute("message", message);
		return "pay";
    }
	
	/**
	 * 支付入口，首先唤起用户授权
	 * @param alipayInfo
	 * @return
	 */
	@RequestMapping(value="/alipay/getcode")
    public String  getAuthCode(HttpServletRequest request) {
		Map<Object,Object> map = GeneralUtil.mapList(request);
		
		String redirectUri = AliPayConfig.redirectUri + "?orderId="+map.get("order_id")
							+"&totalAmount="+map.get("total_fee");
		//拼接授权url
		String oauth2Url = GeneralUtil.oAuthUrl(AliPayConfig.APPID,redirectUri,AliPayConfig.scope);
		//跳转到授权url
		return "redirect:" + oauth2Url;
    }

	/**
	 * 授权跳转地址，并唤起支付
	 * @param request
	 * @param response
	 * @param auth_code
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/alipay/pay")
    public ModelAndView  getTest1(HttpServletRequest request,HttpServletResponse response, @RequestParam String auth_code,@RequestParam String orderId,@RequestParam String totalAmount) {
		logger.info("保存获取的用户信息");
		aliPayService.addZfbUsrInfo(auth_code,orderId);
		logger.info("手机H5支付"); 
		String form = aliPayService.aliPayMobile(orderId,totalAmount);
		ModelAndView mv = new ModelAndView("pay");
		mv.addObject("form",form);
		return mv;
	}
	
	/**
	 * 异步回调,支付宝异步回调不能做转发
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/alipay/alipayNotifyUrl",method=RequestMethod.POST)
	public String alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String  message = "failed";
		Map<String, String> params = new HashMap<String, String>();
		// 取出所有参数是为了验证签名
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			params.put(parameterName, request.getParameter(parameterName));
		}
		//验证签名 校验签名
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET,AliPayConfig.SIGNTYPE);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if (signVerified) {
			logger.info("支付宝验证签名成功！");
			// 若参数中的appid和填入的appid不相同，则为异常通知
			if (!AliPayConfig.APPID.equals(params.get("app_id"))) {
				logger.info("与付款时的appid不同，此为异常通知，应忽略！");
			}else{
				String outtradeno = params.get("out_trade_no");
				//在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
				String status = params.get("trade_status");
				if (status.equals("WAIT_BUYER_PAY")) { // 如果状态是正在等待用户付款
					logger.info(outtradeno + "订单的状态正在等待用户付款");
					HashMap<String,String> map = new HashMap<String,String>();
				    map.put("orderId", request.getParameter("out_trade_no"));
					String rtInfo=JsonUtil.toJson(map, ResultStatus.ALIPAYWAITBUYERPAY, ResultMessage.ALIPAYWAITBUYERPAY);
				    HashMap<String,Object> map1 = new HashMap<String,Object>();
				    map1.put("rtInfo", rtInfo);
				    String uri=AliPayConfig.tvmQrNotifyUrl;
				    String rtInfos =HttpUtil.doPost(uri, map1);
				} else if (status.equals("TRADE_CLOSED")) { // 如果状态是未付款交易超时关闭，或支付完成后全额退款
					logger.info(outtradeno + "订单的状态已经关闭");
					HashMap<String,String> map = new HashMap<String,String>();
				    map.put("orderId", request.getParameter("out_trade_no"));
					String rtInfo=JsonUtil.toJson(map, ResultStatus.ALIPAYTRADECLOSED, ResultMessage.ALIPAYTRADECLOSED);
				    HashMap<String,Object> map1 = new HashMap<String,Object>();
				    map1.put("rtInfo", rtInfo);
				    String uri=AliPayConfig.tvmQrNotifyUrl;
				    String rtInfos =HttpUtil.doPost(uri, map1);
				} else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { // 如果状态是已经支付成功
					logger.info("(支付宝订单号:"+outtradeno+"付款成功)");
					message = "success";
					//调用业务接口，传递支付宝支付返回信息
					HashMap<String,String> map = new HashMap<String,String>();
				    map.put("orderId", request.getParameter("out_trade_no"));
				   /* List<AlipayInfo> list = alipayDao.selectAlipayInfo(map);
				    params.put("user_id", list.get(0).getUserId());*/
				    String rtInfo=JsonUtil.toJson(map, ResultStatus.ALIPAYTRADESUCCESS, ResultMessage.ALIPAYTRADESUCCESS);
				    HashMap<String,Object> map1 = new HashMap<String,Object>();
				    map1.put("rtInfo", rtInfo);
				    String uri=AliPayConfig.tvmQrNotifyUrl;
				    String rtInfos =HttpUtil.doPost(uri, map1);
				} 
			}
		} else { // 如果验证签名没有通过
			message =  "failed";
			logger.info("验证签名失败！");
			HashMap<String,String> map = new HashMap<String,String>();
		    map.put("orderId", request.getParameter("out_trade_no"));
			String rtInfo=JsonUtil.toJson(map, ResultStatus.ALIPAYSIGNVERIFIEDFAILED, ResultMessage.ALIPAYSIGNVERIFIEDFAILED);
		    HashMap<String,Object> map1 = new HashMap<String,Object>();
		    map1.put("rtInfo", rtInfo);
		    String uri=AliPayConfig.tvmQrNotifyUrl;
		    String rtInfos =HttpUtil.doPost(uri, map1);
		}
		return message;
	}
	
	/**
	 * 同步通知
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/alipay/alipayReturnUrl")
	@ResponseBody
	public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String  message = "success";
		// 取出所有参数是为了验证签名
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			params.put(parameterName, request.getParameter(parameterName));
		}
		//验证签名 校验签名
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET,AliPayConfig.SIGNTYPE);
		} catch (AlipayApiException e) {
			e.printStackTrace();
			message =  "failed";
		}
		if(signVerified){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//该页面可做页面美工编辑
			logger.info("同步通知成功》》》》》》");
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
	
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			//该页面可做页面美工编辑
			
		}
		return message;
	}
	
}
