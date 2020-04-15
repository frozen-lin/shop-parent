package com.frozen.pc.web.utils;

import com.frozen.pc.web.comm.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> httpClient工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-14 16:43
 **/
public class HttpClientUtil {
    private static HttpClient httpClient = SpringContextUtil.getBean(HttpClient.class);

    /**
     * <description> get请求 </description>
     *
     * @param url             : url地址
     * @param headers         : 请求头 如无需配置传null即可
     * @param params          : 请求参数 如无需配置传null即可
     * @param responseHandler :  响应处理
     * @return : T
     * @author : lw
     * @date : 2020/4/14 20:34
     */
    public static <T> T doGet(String url, Map<String, String> headers, Map<String, String> params, ResponseHandler<T> responseHandler) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            params.forEach(uriBuilder::addParameter);
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        if (headers != null) {
            headers.forEach(httpGet::addHeader);
        }
        return httpClient.execute(httpGet, responseHandler);
    }

    /**
     * <description> post请求 </description>
     *
     * @param url             : url地址
     * @param headers         : 请求头 如无需配置传null即可
     * @param params          : 请求参数 如无需配置传null即可
     * @param responseHandler :  响应处理
     * @return : T
     * @author : lw
     * @date : 2020/4/14 20:34
     */
    public static <T> T doPost(String url, Map<String, String> headers, Map<String, String> params, ResponseHandler<T> responseHandler) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            headers.forEach(httpPost::addHeader);
        }
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<>(params.size());
            params.forEach((key, value) -> parameters.add(new BasicNameValuePair(key, value)));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, WebConstants.DEFAULT_CHARSET));
        }
        return httpClient.execute(httpPost, responseHandler);
    }

    /**
     * <description> post请求 </description>
     *
     * @param url             : url地址
     * @param headers         : 请求头 如无需配置传null即可
     * @param paramsJson      : 请求体json串 如无需配置传null即可
     * @param responseHandler :  响应处理
     * @return : T
     * @author : lw
     * @date : 2020/4/14 20:34
     */
    public static <T> T doPostByJson(String url, Map<String, String> headers, String paramsJson, ResponseHandler<T> responseHandler) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }
        if (StringUtils.isNotBlank(paramsJson)) {
            httpPost.setEntity(new StringEntity(paramsJson, ContentType.APPLICATION_JSON));
        }
        return httpClient.execute(httpPost, responseHandler);
    }
}
