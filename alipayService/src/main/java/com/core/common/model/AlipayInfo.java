package com.core.common.model;
import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
/**
 * 产品订单信息
 * @author jian.wang
 *
 */
public class AlipayInfo implements Serializable {
	
	/**
	 * @Null 限制只能为null
	 * @NotNull 限制必须不为null
	 * @AssertFalse 限制必须为false
	 * @AssertTrue 限制必须为true
	 * @DecimalMax(value) 限制必须为一个不大于指定值的数字
	 * @DecimalMin(value) 限制必须为一个不小于指定值的数字
	 * @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
	 * @Future 限制必须是一个将来的日期
	 * @Max(value) 限制必须为一个不大于指定值的数字
	 * @Min(value) 限制必须为一个不小于指定值的数字
	 * @Past 限制必须是一个过去的日期
	 * @Pattern(value) 限制必须符合指定的正则表达式
	 * @Size(max,min) 限制字符长度必须在min到max之间
	 * @Past 验证注解的元素值（日期类型）比当前时间早
	 * @NotEmpty 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
	 * @NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
	 * @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
	 */
	
	private static final long serialVersionUID = 1L;

	// 商户网站的订单系统中的唯一订单号，非支付宝交易号。
	private String orderId;
	// 支付宝交易号
	private String tradeNo;
	//用户ID
	private String userId;
	// 总金额(单位是分)
	private String totalAmount;
	//产品码
	private String productCode;
	//授权码
	private String authCode;
	//订单金额
	private String total_fee;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
