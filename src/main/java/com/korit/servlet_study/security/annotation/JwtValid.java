package com.korit.servlet_study.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})  // 메소드 위에 사용가능
@Retention(RetentionPolicy.RUNTIME) // 어노테이션을 언제적용시킬지 정하는 시점
public @interface JwtValid {

}
