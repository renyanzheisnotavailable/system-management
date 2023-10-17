package com.example.common.annotation;

import com.example.common.constant.RoleConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
     String value() default RoleConstant.ROLE_USER;
}
