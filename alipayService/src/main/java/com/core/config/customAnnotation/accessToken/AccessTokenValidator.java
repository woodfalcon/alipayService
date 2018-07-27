package com.core.config.customAnnotation.accessToken;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccessTokenValidator implements ConstraintValidator<AccessToken, String>{

	@Override
	public boolean isValid(String accesstoken, ConstraintValidatorContext context) {
		
		//accesstoken进行数据合法性校验
		//判断accesstoken是否合法
		
		
		
		System.err.println(">>>:"+accesstoken+"<<<<<<"+context);
		
		return false;
	}

	
}
