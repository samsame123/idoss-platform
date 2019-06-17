package com.idoss.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by xiaoxuwang on 2019/6/6.
 */
@Data
@AllArgsConstructor
public class BizException extends Exception {

    private String code;

    private String message;

    public BizException(BaseErrorCode error, String... errorMsg) {
        this.code = error.getCode();
        StringBuilder msg = new StringBuilder();
        for(String m : errorMsg) {
            msg.append(",");
            msg.append(m);
        }
        this.message = error.getMessage()+msg;
    }

    public BizException(BaseErrorCode error, String errorMsg, boolean flag) {
        this.code = error.getCode();
        String msg = String.format(error.getMessage(), errorMsg);
        this.message = msg;
    }

}
