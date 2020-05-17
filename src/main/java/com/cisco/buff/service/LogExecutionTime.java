package com.cisco.buff.service;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author bharatsi
 *
 */
public @interface LogExecutionTime {
	public TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}