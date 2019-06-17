package com.idoss.support.http;

import com.idoss.api.exception.BizException;
import org.apache.http.HttpResponse;

/**
 * Created by xiaoxuwang on 2019/6/13.
 */
public interface HttpResponseHandler {

    String doCheck(HttpResponse response) throws BizException;

}
