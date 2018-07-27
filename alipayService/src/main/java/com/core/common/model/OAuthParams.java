package com.core.common.model;

import javax.validation.constraints.NotEmpty;

public class OAuthParams {

	//授权回调地址
	@NotEmpty(message = "参数【redirectUri】不能为空")
	private String redirectUri;
	
	//appId
	@NotEmpty(message = "参数【appId】不能为空")
	private String appId;
	
	//接口权限值scope
	@NotEmpty(message = "参数【scope】不能为空")
	private String scope;
		
	
	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

		
}
