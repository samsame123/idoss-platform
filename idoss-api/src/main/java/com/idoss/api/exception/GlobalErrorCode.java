package com.idoss.api.exception;

import lombok.Getter;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Getter
public enum GlobalErrorCode implements BaseErrorCode {

    UNKNOWN("999", "未知错误");

    private String code;

    private String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
