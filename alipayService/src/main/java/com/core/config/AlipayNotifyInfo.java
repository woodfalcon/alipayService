package com.core.config;

public class AlipayNotifyInfo {
	/*
	 * {gmt_create=2018-06-22 14:09:19, charset=UTF-8,
	 * seller_email=fangzhengguoji@163.com, subject=ceshi,
	 * sign=CF57h8Ok6skuPvNe9VFLjYO4sg
	 * pX3bgFTgaX/NHK3OpalKvd6Q/xg0XVw6NcgP+n41nM2eOzHZX+ujDH8BFVtFEHAYMB+
	 * tZMmLJLwAZnb9M4x0nxLXf7XNUG6z0OBO/
	 * isxTMVVntBRPlDLRFl8if4dPiR3hyucJUvbMDV7OA3bQHOlnn9tVz7qK+
	 * DQFZ3RDpTDJ7qOmtYP5jB3wV8SPv/gmT01gOT++
	 * we02akHArqQ2ZrgSuKdS9hhQObev5i3bZQ4jJaMcbcPbrDmW2NaUv2ji4m7qQHqwFlxfLxw7vWfq5KOJuBJnxA80hXCo
	 * +LfU+QdiUViVe7a5qWxRkxppfvQ==, 
	 * body=ceshibody, 
	 * buyer_id=2088712198348605,
	 * 
	 * invoice_amount=0.01, notify_id=383900c87d7564bc418c449a02c9ab1kmt,
	 * fund_bill_list=[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}],
	 * notify_type=trade_status_sync, trade_status=TRADE_SUCCESS,
	 * receipt_amount=0.01, buyer_pay_amount=0.01, app_id=2017062407557967,
	 * sign_type=RSA2, seller_id=2088721085094677, gmt_payment=2018-06-22
	 * 14:09:20, notify_time=2018-06-22 14:09:20, version=1.0,
	 * out_trade_no=201806221408, total_amount=0.01,
	 * trade_no=2018062221001004600516057265, auth_app_id=2017062407557967,
	 * buyer_logon_id=180***@qq.com, point_amount=0.00}
	 */
	//卖家邮箱
	private String seller_email;
	//商品名称
	private String subject;
	//商品描述
	private String body;
	//买家支付宝用户号
	private String buyer_id;
	//开票金额,用户在交易中支付的可开发票的金额，单位为元，精确到小数点后2位
	private String invoice_amount;
	//交易状态
	private String trade_status;
	//实收金额,商家在交易中实际收到的款项，单位为元，精确到小数点后2位
	private String receipt_amount;
	//付款金额
	private String buyer_pay_amount;
	//订单金额
	private String total_amount;
	//支付宝交易流水号
	private String trade_no;
	//集分宝金额,使用集分宝支付的金额，单位为元，精确到小数点后2位
	private String point_amount;
	//买家登陆账号，买家支付宝账号
	private String buyer_logon_id;
	
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getReceipt_amount() {
		return receipt_amount;
	}
	public void setReceipt_amount(String receipt_amount) {
		this.receipt_amount = receipt_amount;
	}
	public String getBuyer_pay_amount() {
		return buyer_pay_amount;
	}
	public void setBuyer_pay_amount(String buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getPoint_amount() {
		return point_amount;
	}
	public void setPoint_amount(String point_amount) {
		this.point_amount = point_amount;
	}
	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}
	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

}
