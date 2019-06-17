package com.idoss.support.constant;

import lombok.Getter;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Getter
public enum RedisIndex {

    WECHAT_ACCESS_TOKEN("WECHAT:ACCESS:TOKEN:%s"),
    WECHAT_JSAPI_TICKET("WECHAT:JSAPI:TICKET:%s"),
    WECHAT_CARDAPI_TICKET("WECHAT:CARDAPI:TICKET:%s"),
    WECHAT_USER_SESSION_KEY("WECHAT:USER:SESSION:KEY:%s");

    private final String index;

    RedisIndex(String index){
        this.index = index;
    }

}
