package com.idoss.support.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoxuwang on 2019/6/13.
 */
@Slf4j
public class DefaultHttpBodyBuilder {

    private HttpPost httpPost;

    private HttpGet httpGet;

    private HttpPut httpPut;

    private Map<String, Object> mapParams;

    private String uri;

    private String charset;

    private DefaultHttpBodyBuilder(String uri) {
        httpPost = new HttpPost(uri);
        httpPut = new HttpPut(uri);
        this.uri = uri;
    }

    public static class Builder {

        private Map<String, Object> mapParams;

        private String charset = "utf-8";

        private String uri;

        public Builder(String uri) {
            this.uri = uri;
        }

        /**
         * 请求参数传入，类型为Map<String,Object>
         */
        public Builder setMapParams(Map<String, Object> mapParams) {
            this.mapParams = mapParams;
            return this;
        }

        /**
         * 设置字符编码，默认为utf-8
         */
        public Builder setCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public HttpPost buildPost() {
            return new DefaultHttpBodyBuilder(uri).buildHttpPost(this);
        }

        public HttpGet buildGet() {
            return new DefaultHttpBodyBuilder(uri).buildHttpGet(this);
        }

        public HttpPut buildPut() {
            return new DefaultHttpBodyBuilder(uri).buildHttpPut(this);
        }

    }

    private HttpPost buildHttpPost(Builder builder) {
        this.mapParams = builder.mapParams;
        this.charset = builder.charset;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(buildPostParam(), charset));
        } catch (UnsupportedEncodingException e) {
            log.error("build HttpPost fail, occurUnsupportedEncodingException ，" +
                    " mapParams:" + mapParams.toString() + " charset:" + charset, e);
        }
        return httpPost;
    }

    private HttpGet buildHttpGet(Builder builder) {
        this.mapParams = builder.mapParams;
        this.charset = builder.charset;
        httpGet = new HttpGet(buildGetParam(uri));
        return httpGet;
    }

    private HttpPut buildHttpPut(Builder builder) {
        this.mapParams = builder.mapParams;
        this.charset = builder.charset;
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(buildPostParam(), charset));
        } catch (UnsupportedEncodingException e) {
            log.error("build HttpPut fail, occurUnsupportedEncodingException ，" +
                    " mapParams:" + mapParams.toString() + " charset:" + charset, e);
        }
        return httpPut;
    }

    private List<NameValuePair> buildPostParam() {
        List<NameValuePair> params = new ArrayList<>();
        if (null != mapParams && 0 < mapParams.size()) {
            for (Map.Entry<String, Object> e : mapParams.entrySet()) {
                params.add(new BasicNameValuePair(e.getKey(), (String) e.getValue()));
            }
        }
        return params;
    }

    private String buildGetParam(String uri) {
        StringBuffer stringBuffer = new StringBuffer(uri);
        if (null != mapParams && 0 < mapParams.size()) {
            for (Map.Entry<String, Object> e : mapParams.entrySet()) {
                if (stringBuffer.indexOf("?") <= -1) {
                    stringBuffer.append("?");
                    stringBuffer.append(e.getKey() + "=" + e.getValue());
                } else {
                    stringBuffer.append("&" + e.getKey() + "=" + e.getValue());
                }
            }
        }
        log.info("build param success: " + stringBuffer.toString());
        return stringBuffer.toString();
    }

}
