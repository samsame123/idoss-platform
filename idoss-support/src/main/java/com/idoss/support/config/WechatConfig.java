package com.idoss.support.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.idoss.support.util.abstracted.AbstractJedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
public class WechatConfig {

    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    @Autowired
    @Qualifier(value = "jedis0")
    private AbstractJedis jedis;

    @Bean
    public WxMaConfig wxMaConfig() {
        WechatRedisStorageConfig config = new WechatRedisStorageConfig(jedis);
        config.setAppid(appId);
        config.setSecret(appSecret);
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }

}
