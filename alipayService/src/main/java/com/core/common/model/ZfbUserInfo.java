package com.core.common.model;

/**
 * 产品订单信息
 * @author jian.wang
 *
 */
public class ZfbUserInfo {
	
	//用户ID
	private String userId;
	//身份证号
	private String certno;
	//姓名
	private String uname;
	//手机号
	private String phone;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
