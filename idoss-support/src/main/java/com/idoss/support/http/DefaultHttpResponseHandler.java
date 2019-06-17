package com.idoss.support.http;

import com.idoss.api.exception.BizException;
import com.idoss.support.error.IdossMainErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by xiaoxuwang on 2019/6/13.
 */
@Slf4j
public class DefaultHttpResponseHandler implements HttpResponseHandler{

    @Override
    public String doCheck(HttpResponse response) throws BizException {
        String result = null;
        if(null == response) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        if(entity != null){
            try {
                result = EntityUtils.toString(entity, "utf-8");
                log.info("success to handle response，result：" + result);
            }catch (IOException e){
                log.error("error to handle response", e);
                throw new BizException(IdossMainErrorCode.HANDLE_RESPONSE_ERROR);
            }
        }
        return result;
    }
}
