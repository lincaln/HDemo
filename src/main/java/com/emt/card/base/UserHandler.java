package com.emt.card.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserHandler {

	boolean tokenCheck() default true;
	
	boolean cardCheck() default false;
	
	boolean perCheck() default true;
}
