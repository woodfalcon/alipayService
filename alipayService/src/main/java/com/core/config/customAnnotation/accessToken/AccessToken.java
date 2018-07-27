package com.core.config.customAnnotation.accessToken;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AccessTokenValidator.class)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessToken {
	   String message() default "AccessToken不合法";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};
}
