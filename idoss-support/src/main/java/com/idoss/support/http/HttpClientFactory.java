package com.idoss.support.http;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created by xiaoxuwang on 2019/6/12.
 */
@Slf4j
@Setter
public class HttpClientFactory {

    private PoolingHttpClientConnectionManager poolManager;

    private int connectionTimeout;

    private int socketTimeout;

    private HttpRequestRetryHandler httpRequestRetryHandler;

    public CloseableHttpClient getHttpClient() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setCookieSpec(CookieSpecs.STANDARD).build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolManager)
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setRetryHandler(httpRequestRetryHandler)
                .build();

        return httpClient;
    }


}
