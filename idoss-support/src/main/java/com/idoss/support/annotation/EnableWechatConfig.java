package com.idoss.support.annotation;

import com.idoss.support.config.WechatConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WechatConfig.class)
public @interface EnableWechatConfig {
}
