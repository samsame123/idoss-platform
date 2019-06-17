package com.idoss.support.config;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.idoss.api.exception.BizException;
import com.idoss.support.constant.RedisIndex;
import com.idoss.support.util.abstracted.AbstractJedis;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Data
@Slf4j
public class WechatRedisStorageConfig implements WxMaConfig {

    private AbstractJedis jedisUtil;
    protected volatile String msgDataFormat;
    protected volatile String appid;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String aesKey;
    protected volatile long expiresTime;
    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;
    protected Lock accessTokenLock = new ReentrantLock();
    protected Lock jsapiTicketLock = new ReentrantLock();
    protected Lock cardApiTicketLock = new ReentrantLock();
    protected volatile File tmpDirFile;
    protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    public WechatRedisStorageConfig(AbstractJedis jedisUtil){
        this.jedisUtil = jedisUtil;
    }

    @Override
    public String getAccessToken() {
        String result = null;
        try {
            result = jedisUtil.get(String.format(RedisIndex.WECHAT_ACCESS_TOKEN.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        long result = 0l;
        try {
            result = jedisUtil.getTTL(String.format(RedisIndex.WECHAT_ACCESS_TOKEN.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result < 10;
    }

    @Override
    public void expireAccessToken() {
        try {
            jedisUtil.del(String.format(RedisIndex.WECHAT_ACCESS_TOKEN.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken wxAccessToken) {
        this.updateAccessToken(wxAccessToken.getAccessToken(), wxAccessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String s, int i) {
        try {
            jedisUtil.set(String.format(RedisIndex.WECHAT_ACCESS_TOKEN.getIndex(), appid), s, i);
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public String getJsapiTicket() {
        String result = null;
        try {
            result = jedisUtil.get(String.format(RedisIndex.WECHAT_JSAPI_TICKET.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        long result = 0l;
        try {
            result = jedisUtil.getTTL(String.format(RedisIndex.WECHAT_JSAPI_TICKET.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result < 10;
    }

    @Override
    public void expireJsapiTicket() {
        try {
            jedisUtil.del(String.format(RedisIndex.WECHAT_JSAPI_TICKET.getIndex(),appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public synchronized void updateJsapiTicket(String s, int i) {
        try {
            jedisUtil.set(String.format(RedisIndex.WECHAT_JSAPI_TICKET.getIndex(),appid), s, i);
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public String getCardApiTicket() {
        String result = null;
        try {
            result = jedisUtil.get(String.format(RedisIndex.WECHAT_CARDAPI_TICKET.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Lock getCardApiTicketLock() {
        return this.cardApiTicketLock;
    }

    @Override
    public boolean isCardApiTicketExpired() {
        long result = 0l;
        try {
            result = jedisUtil.getTTL(String.format(RedisIndex.WECHAT_CARDAPI_TICKET.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
        return result < 10;
    }

    @Override
    public void expireCardApiTicket() {
        try {
            jedisUtil.del(String.format(RedisIndex.WECHAT_CARDAPI_TICKET.getIndex(), appid));
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public synchronized void updateCardApiTicket(String s, int i) {
        try {
            jedisUtil.set(String.format(RedisIndex.WECHAT_CARDAPI_TICKET.getIndex(), appid), s, i);
        } catch (BizException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

}
