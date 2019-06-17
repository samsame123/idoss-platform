package com.idoss.support.http;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * Created by xiaoxuwang on 2019/6/12.
 */
@Slf4j
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestRetry implements HttpRequestRetryHandler{

    private int maximumNumberOfRetry;

    @Override
    public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
        if (i >= maximumNumberOfRetry) {
            log.error("Retry fail ,reason：reach the upper limit！");
            return false;
        }
        if (e instanceof InterruptedIOException) {
            log.error("Retry fail ,reason：occur InterruptedIOException", e);
            return false;
        }
        if (e instanceof UnknownHostException) {
            log.error("Retry fail ,reason：occur UnknownHostException", e);
            return false;
        }
        if (e instanceof ConnectTimeoutException) {
            log.error("Retry fail ,reason：occur ConnectTimeoutException", e);
            return false;
        }
        if (e instanceof SSLException) {
            log.error("Retry fail ,reason：occur SSLException", e);
            return false;
        }
        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            log.info("Retry success ,times:"+i);
            return true;
        }
        return false;
    }
}
