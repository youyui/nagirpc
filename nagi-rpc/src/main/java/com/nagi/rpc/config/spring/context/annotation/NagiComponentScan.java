package com.nagi.rpc.config.spring.context.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义扫描Nagi组件范围，扫描@NagiService,@NagiReference
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(NagiComponentScanRegistrar.class)
public @interface NagiComponentScan {
    /*
    一个路径时
     */
    String value() default "";
    /*
    多个路径时
     */
    String[] packages() default {};

}
