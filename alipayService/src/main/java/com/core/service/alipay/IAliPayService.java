package com.core.service.alipay;

import com.core.common.model.QueryInfo;
import com.core.common.model.AlipayInfo;
import com.core.common.model.SignParams;

/**
 * 扫码支付以及手机H5支付
 * @author jian.wang
 *
 */
public interface IAliPayService {
	/**
	 * 阿里支付退款
	 * @param alipayInfo
	 * @return
	 */
	String aliRefund(AlipayInfo alipayInfo);
	/**
	 * 关闭订单
	 * @param alipayInfo
	 * @return
	 */
	String aliCloseorder(AlipayInfo AlipayInfo);
	/**
	 * 下载对账单 
	 * @param billDate
	 * @param billType
	 * @return
	 */
	String downloadBillUrl(String billDate,String billType);
	/**
	 *  手机H5支付、腾讯相关软件下不支持、使用UC等浏览器打开
	 * 方法一：
	 * 对于页面跳转类API，SDK不会也无法像系统调用类API一样自动请求支付宝并获得结果，而是在接受request请求对象后，
	 * 为开发者生成前台页面请求需要的完整form表单的html（包含自动提交脚本），商户直接将这个表单的String输出到http response中即可。
	 * 方法二：
	 * 如果是远程调用返回消费放一个form表单 然后调用方刷新到页面自动提交即可
	 * @param alipayInfo
	 * @return
	 * 备注：人民币单位为分
	 * attach 附件参数 使用json格式传递 用于回调区分
	 */
	String aliPayMobile(String orderId,String totalAmount);
	
	/**
	 * 交易查询
	 * @param alipayInfo
	 * @return
	 */
	String aliQuery(QueryInfo queryInfo);
	
	/**
	 * 退款交易查询
	 * @param alipayInfo
	 * @return
	 */
	String aliRefundQuery(QueryInfo queryInfo);
	
	/**
	 * 退款交易查询
	 * @param alipayInfo
	 * @return
	 */
	boolean aliSignVerified(SignParams signParams);
	
	/**获取userId
	 * 
	 * @param alipayInfo
	 * @return
	 */
	void addZfbUsrInfo(String authCode,String orderId);
	
	/**
	 * 添加支付信息
	 * @param alipayInfo
	 */
	void addPayInfo(AlipayInfo alipayInfo);
}
