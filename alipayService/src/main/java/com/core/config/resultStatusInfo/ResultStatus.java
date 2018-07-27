package com.core.config.resultStatusInfo;

/**
 * 结果状态码
 * @author jian.wang
 *
 */
public class ResultStatus {
	/**
	 * 成功SUCCESS
	 **/
	//支付成功
	public static final String ALIPAYTRADESUCCESS = "50001";
	
	/**
	 * 失败
	 **/
	//等待用户付款
	public static final String ALIPAYWAITBUYERPAY = "60001";
	//订单关闭
	public static final String ALIPAYTRADECLOSED = "60002";
	//验证签名失败
	public static final String ALIPAYSIGNVERIFIEDFAILED = "60003";
	
}
