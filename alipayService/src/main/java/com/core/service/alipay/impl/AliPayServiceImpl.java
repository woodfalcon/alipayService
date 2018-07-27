package com.core.service.alipay.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.core.common.constants.Constants;
import com.core.common.model.OAuthParams;
import com.core.common.model.QueryInfo;
import com.core.common.model.AlipayInfo;
import com.core.common.model.SignParams;
import com.core.common.utils.CommonUtil;
import com.core.service.alipay.IAliPayService;
import com.core.config.AliPayConfig;
import com.core.config.JsonUtil;
import com.core.dao.alipay.AlipayDao;
/**
 * 
 * @author jian.wang
 *
 */
@Service
public class AliPayServiceImpl implements IAliPayService {
	private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
	String form = "";
	
	@Autowired
	private AlipayDao alipayDao;
	
	@Override
	public String aliRefund(AlipayInfo alipayInfo) {
		logger.info("订单号："+alipayInfo.getOrderId()+"支付宝退款");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
		
		String  message = Constants.SUCCESS;
		 // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = alipayInfo.getOrderId();
        String tradeNo = alipayInfo.getTradeNo();
        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = CommonUtil.divide(alipayInfo.getTotalAmount(), "100").toString();

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";
        
        AlipayClient alipayClient = new DefaultAlipayClient(
    			AliPayConfig.URL,AliPayConfig.APPID,AliPayConfig.RSA_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY,AliPayConfig.SIGNTYPE);
    	
        AlipayTradeRefundRequest arequest = new AlipayTradeRefundRequest();
    	
    	AlipayTradeRefundModel model=new AlipayTradeRefundModel();
    	model.setOutTradeNo(outTradeNo);
    	model.setTradeNo(tradeNo);
    	model.setRefundAmount(refundAmount);
    	model.setRefundReason(refundReason);
    	//model.setOutRequestNo(out_request_no);
    	//model.setStoreId(storeId);
    	arequest.setBizModel(model);
    	
    	AlipayTradeRefundResponse alipay_response;
		try {
			alipay_response = alipayClient.execute(arequest);
			//更新支付状态
            HashMap<String,String>map = new HashMap<String,String>();
             map.put("orderId", outTradeNo);
			 map.put("payState", "4");//支付状态4：已退款
             map.put("timeState", timeNow);
             alipayDao.updateZfbState(map);
			System.out.println(alipay_response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
    	
		return message;
	}
    /**
     * 如果你调用的是当面付预下单接口(alipay.trade.precreate)，调用成功后订单实际上是没有生成，因为创建一笔订单要买家、卖家、金额三要素。
     * 预下单并没有创建订单，所以根据商户订单号操作订单，比如查询或者关闭，会报错订单不存在。
     * 当用户扫码后订单才会创建，用户扫码之前二维码有效期2小时，扫码之后有效期根据timeout_express时间指定。
     * =====只有支付成功后 调用此订单才可以=====
     */
	@Override
	public String aliCloseorder(AlipayInfo alipayInfo) {
		logger.info("订单号："+alipayInfo.getOrderId()+"支付宝关闭订单");
		String  message = Constants.SUCCESS;
		try {
			String imgPath= Constants.QRCODE_PATH+Constants.SF_FILE_SEPARATOR+"alipay_"+alipayInfo.getOrderId()+".png";
			File file = new File(imgPath);
            if(file.exists()){
            	
            	 AlipayClient alipayClient = new DefaultAlipayClient(
             			AliPayConfig.URL,AliPayConfig.APPID,AliPayConfig.RSA_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY,AliPayConfig.SIGNTYPE);
             	
                AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            	request.setBizContent("{" +
            			"    \"out_trade_no\":\""+alipayInfo.getOrderId()+"\"" +
            			"  }");
            	AlipayTradeCloseResponse response = alipayClient.execute(request);
            	if(response.isSuccess()){//扫码未支付的情况
            		logger.info("订单号："+alipayInfo.getOrderId()+"支付宝关闭订单成功并删除支付二维码");
            		file.delete();
            	}else{
            		if("ACQ.TRADE_NOT_EXIST".equals(response.getSubCode())){
            			logger.info("订单号："+alipayInfo.getOrderId()+response.getSubMsg()+"(预下单 未扫码的情况)");
            		}else if("ACQ.TRADE_STATUS_ERROR".equals(response.getSubCode())){
            			logger.info("订单号："+alipayInfo.getOrderId()+response.getSubMsg());
            		}else{
            			logger.info("订单号："+alipayInfo.getOrderId()+"支付宝关闭订单失败"+response.getSubCode()+response.getSubMsg());
            			message = Constants.FAIL;
            		}
            	}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = Constants.FAIL;
			logger.info("订单号："+alipayInfo.getOrderId()+"支付宝关闭订单异常");
		}
		return message;
	}
	@Override
	public String downloadBillUrl(String billDate,String billType) {
		logger.info("获取支付宝订单地址:"+billDate);
		String downloadBillUrl = "";
		/*try {
			AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{" + "    \"bill_type\":\"trade\","
					                   + "    \"bill_date\":\"2016-12-26\"" + "  }");
			
			AlipayDataDataserviceBillDownloadurlQueryResponse response
			                          = alipayClient.execute(request);
			if (response.isSuccess()) {
				logger.info("获取支付宝订单地址成功:"+billDate);
				downloadBillUrl = response.getBillDownloadUrl();//获取下载地
			} else {
				logger.info("获取支付宝订单地址失败"+response.getSubMsg()+":"+billDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取支付宝订单地址异常:"+billDate);
		}*/
		return downloadBillUrl;
	}
	
	@Override
	public String aliPayMobile(String orderId,String totalAmount) {
		logger.info("支付宝手机支付下单");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
		
		AlipayClient alipayClient = new DefaultAlipayClient(
	    			AliPayConfig.URL,AliPayConfig.APPID,AliPayConfig.RSA_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY,AliPayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		// 封装请求支付信息
	    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
	    //对应商户网站的订单系统中的唯一订单号，非支付宝交易号。需保证在商户网站中的唯一性。是请求时对应的参数，原样返回,必填。
    	 model.setOutTradeNo(orderId);
 	    // 订单名称，必填
 	    model.setSubject(AliPayConfig.subject);
 	    // 付款金额，必填
 	    model.setTotalAmount((Double.parseDouble(totalAmount))/100+"");
 	    // 商品描述，可空
 	    model.setBody(AliPayConfig.body);
 	    // 超时关闭该订单时间， 可空
 	    model.setTimeoutExpress(AliPayConfig.timeoutExpress);
 	    //销售产品码，商家和支付宝签约的产品码，为固定值 QUICK_WAP_WAY，必填。
 	    model.setProductCode(AliPayConfig.productCode);
 	    alipayRequest.setBizModel(model);
 	    // 设置异步通知地址
 	    alipayRequest.setNotifyUrl(AliPayConfig.notify_url);
 	    // 设置同步地址
 	    alipayRequest.setReturnUrl(AliPayConfig.return_url);   
         logger.info("业务参数:"+alipayRequest.getBizContent());
         form = Constants.FAIL;
         try {
             form = alipayClient.pageExecute(alipayRequest).getBody();
             //更新支付状态
             HashMap<String,String>map = new HashMap<String,String>();
             map.put("orderId", orderId);
             map.put("payState", "1");//支付状态1：已支付
             map.put("timeState", timeNow);
             map.put("timeStamp", timeNow);
             alipayDao.updateZfbState(map);
         } catch (AlipayApiException e) {
         	logger.error("支付宝构造表单失败",e);
         }
         
        return form;
	}
	
	@Override
	public String aliQuery(QueryInfo queryInfo) {
		logger.info("订单号："+queryInfo.getOrderId()+"交易查询");
		AlipayClient alipayClient = new DefaultAlipayClient(
				AliPayConfig.URL, queryInfo.getAppId(),queryInfo.getRsaPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET, queryInfo.getAlipayPublicKey(),AliPayConfig.SIGNTYPE);
		String  message = Constants.SUCCESS;
		// (必填) 外部订单号，需要退款交易的商户外部订单号
		String out_trade_no = queryInfo.getOrderId();
		//支付宝交易号
		String trade_no = queryInfo.getTradeNo();
		
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
				 
		AlipayTradeQueryModel model=new AlipayTradeQueryModel();
		model.setOutTradeNo(out_trade_no);
		model.setTradeNo(trade_no);
		alipayRequest.setBizModel(model);

		String response = "";
		
		 try {
			AlipayTradeQueryResponse alipayResponse =alipayClient.execute(alipayRequest);
			response = alipayResponse.getBody();
		    System.out.println(alipayResponse.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public String aliRefundQuery(QueryInfo queryInfo) {
		AlipayClient alipayClient = new DefaultAlipayClient(
				AliPayConfig.URL, queryInfo.getAppId(),queryInfo.getRsaPrivateKey(), AliPayConfig.FORMAT, AliPayConfig.CHARSET, queryInfo.getAlipayPublicKey(),AliPayConfig.SIGNTYPE);
		//商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
		//商户订单号，和支付宝交易号二选一
		String out_trade_no = queryInfo.getOrderId();
		//支付宝交易号，和商户订单号二选一
		String trade_no = queryInfo.getTradeNo();
	    //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
		String out_request_no = queryInfo.getOutRequestNo();
		
		AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
		
		AlipayTradeFastpayRefundQueryModel model=new AlipayTradeFastpayRefundQueryModel();
		model.setOutTradeNo(out_trade_no);
		model.setTradeNo(trade_no);
		model.setOutRequestNo(out_request_no);
		alipayRequest.setBizModel(model);
		
		AlipayTradeFastpayRefundQueryResponse alipayResponse;
		String response = "";
		try {
			alipayResponse = alipayClient.execute(alipayRequest);
			response = alipayResponse.getBody();
			System.out.println(response);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public boolean aliSignVerified(SignParams signParams) {
		String verifyInfo=signParams.getVerifyInfo();
		
		Map<String, String> map = new HashMap<String,String>();
		map=JsonUtil.parseJSON2Map(verifyInfo);
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(map,   signParams.getAlipayPublicKey(), AliPayConfig.CHARSET,AliPayConfig.SIGNTYPE);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return signVerified;
	}
	@Override
	public void addZfbUsrInfo(String authCode,String orderId) {
		 AlipayClient alipayClient = new DefaultAlipayClient(
	    			AliPayConfig.URL,AliPayConfig.APPID,AliPayConfig.RSA_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY,AliPayConfig.SIGNTYPE);

		try {
			//使用authCode换取accessToken
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setCode(authCode);
			request.setGrantType("authorization_code");
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
		    String accessToken = oauthTokenResponse.getAccessToken();
		    
		    //使用accessToken获取用户信息
		    AlipayUserInfoShareRequest uInfoRequest = new AlipayUserInfoShareRequest();
		    AlipayUserInfoShareResponse uInfoResponse = alipayClient.execute(uInfoRequest, accessToken);
		 
		    if (uInfoResponse.isSuccess()) {
		        System.out.println("调用成功");
		        System.out.println(ReflectionToStringBuilder.toString(uInfoResponse));
		        String userId = uInfoResponse.getUserId();
		        String uame = uInfoResponse.getUserName();
		        String certno = uInfoResponse.getCertNo();
		        String phone = uInfoResponse.getPhone();
		        HashMap<String,String> map = new HashMap<String,String>();
			    map.put("userId", userId);
			    map.put("uame", uame);
			    map.put("certno", certno);
			    map.put("phone", phone);
			    alipayDao.addZfbUsrInfo(map);
		    } else {
		        System.out.println("调用失败");
		        System.out.println(uInfoResponse.getSubCode() + ":" + uInfoResponse.getSubMsg());
		    } 
		} catch (AlipayApiException e) {
		    //处理异常
		    e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void addPayInfo(AlipayInfo alipayInfo) {
		HashMap<String,String>map = new HashMap<String,String>();
		map.put("orderId", alipayInfo.getOrderId());
		if(alipayDao.selectAlipayInfo(map).size()>0){
			
		}else{
			map.put("authCode", alipayInfo.getAuthCode());
			map.put("body", AliPayConfig.body);
			map.put("productCode", AliPayConfig.productCode);
			map.put("subject", AliPayConfig.subject);
			map.put("totalAmount", alipayInfo.getTotal_fee());
			alipayDao.addAlipayInfo(map);
		}
		
	}
}
