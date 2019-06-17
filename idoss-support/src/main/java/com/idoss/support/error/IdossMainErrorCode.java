package com.idoss.support.error;

import com.idoss.api.exception.BaseErrorCode;
import lombok.Getter;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Getter
public enum IdossMainErrorCode implements BaseErrorCode {

    REDIS_ERROR("10000", "redis操作出现异常"),
    HTTP_CLIENT_ERROR("10001", "请求失败"),
    HANDLE_RESPONSE_ERROR("10002", "请求处理失败");

    private String code;

    private String message;

    IdossMainErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
