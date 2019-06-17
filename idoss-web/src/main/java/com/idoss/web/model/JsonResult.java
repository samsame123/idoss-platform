package com.idoss.web.model;

import com.idoss.api.exception.GlobalErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Data
@AllArgsConstructor
public class JsonResult {

    private static final String SUCCESS = "000";
    private static final String STATUS = "200";

    private String code;
    private String message;
    private Object object;

    public boolean isSuccess() {
        return SUCCESS.equals(code);
    }

    public static JsonResult success() {
        return new JsonResult(SUCCESS, null, null);
    }

    public static JsonResult success(Object object) {
        return new JsonResult(SUCCESS, null, object);
    }

    public static JsonResult fail(Throwable ex) {
        return new JsonResult(GlobalErrorCode.UNKNOWN.getCode(), ex == null ? null : ex.getMessage(), null);
    }

    public static JsonResult fail(String code) {
        return new JsonResult(code, null, null);
    }

    public static JsonResult fail(String code, String message) {
        return new JsonResult(code, message, null);
    }

    public static JsonResult fail(String code, Object data) {
        return new JsonResult(code, null, data);
    }

    public static JsonResult fail(String code, String message, Object object) {
        return new JsonResult(code, message, object);
    }

}
