package com.idoss.support.config;

import com.idoss.support.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Created by xiaoxuwang on 2019/6/12.
 */
public class HttpClientConfig {

    @Value("${http.client.max.total}")
    private int total;

    @Value("${http.client.max.per-rote}")
    private int perRote;

    @Value("${http.client.timeout.connection}")
    private int connection;

    @Value("${http.client.timeout.socket}")
    private int socket;

    @Value("${http.client.retry.times}")
    private int retryTimes;

    @Bean
    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(total);
        pool.setDefaultMaxPerRoute(perRote);
        return pool;
    }

    @Bean
    public HttpRequestRetry getHttpRequestRetry(){
        return new HttpRequestRetry(retryTimes);
    }

    @Bean
    @Scope("prototype")
    public HttpClient getHttpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
                                    HttpRequestRetry httpRequestRetry){
        HttpClientFactory httpClientFactory = new HttpClientFactory();
        httpClientFactory.setPoolManager(poolingHttpClientConnectionManager);
        httpClientFactory.setConnectionTimeout(connection);
        httpClientFactory.setSocketTimeout(socket);
        httpClientFactory.setHttpRequestRetryHandler(httpRequestRetry);
        return httpClientFactory.getHttpClient();
    }

    @Bean
    public HttpResponseHandler getHttpResponseHandler(){
        return new DefaultHttpResponseHandler();
    }

    @Bean
    public HttpUriRequestSender getHttpUriRequestSender(HttpResponseHandler httpResponseHandler, ApplicationContext context){
        return new HttpUriRequestSender(httpResponseHandler, context);
    }
}
