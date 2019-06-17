package com.idoss.support.http;

import com.idoss.api.exception.BizException;
import com.idoss.support.error.IdossMainErrorCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Created by xiaoxuwang on 2019/6/13.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class HttpUriRequestSender {

    private HttpResponseHandler httpResponseHandler;

    private ApplicationContext applicationContext;

    private HttpClient methodInject() {
        return applicationContext.getBean(HttpClient.class);
    }

    public String send(HttpUriRequest httpBody) throws BizException {
        HttpResponse httpResponse = null;
        HttpClient httpClient = methodInject();
        try{
            httpResponse = httpClient.execute(httpBody);
            return httpResponseHandler.doCheck(httpResponse);
        } catch (IOException e) {
            log.error("http client send fail,reason：occur IOException", e);
            throw new BizException(IdossMainErrorCode.HTTP_CLIENT_ERROR);
        } catch (Exception e) {
            log.error("http client send fail,reason：occur Exception", e);
            throw new BizException(IdossMainErrorCode.HTTP_CLIENT_ERROR);
        }
    }



}
