package com.idoss.support.annotation;

import com.idoss.support.config.JedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = JedisConfig.class)
public @interface EnableJedisConfig {
}
