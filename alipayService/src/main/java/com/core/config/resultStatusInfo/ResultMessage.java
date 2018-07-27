package com.core.config.resultStatusInfo;

/**
 * 结果信息
 * @author jian.wang
 *
 */
public class ResultMessage {
	/**
	 * 成功
	 **/ 
	//支付成功
	public static final String ALIPAYTRADESUCCESS = "支付成功!";
	
	/**
	 * 失败
	 **/
	//等待用户付款
	public static final String ALIPAYWAITBUYERPAY = "等待用户付款";
	//订单关闭
	public static final String ALIPAYTRADECLOSED = "订单关闭";
	//验证签名失败
	public static final String ALIPAYSIGNVERIFIEDFAILED = "验证签名失败";
}
