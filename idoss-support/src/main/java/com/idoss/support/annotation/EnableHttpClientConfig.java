package com.idoss.support.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(com.idoss.support.config.HttpClientConfig.class)
public @interface EnableHttpClientConfig {
}
