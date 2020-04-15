package com.frozen.pc.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.frozen.pc.web.bean.GitHubAuthInfo;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> gitHub授权配置类 </description>
 *
 * @author : lw
 * @date : 2020-04-12 13:23
 **/
@Slf4j
public class GitHubUtil {

    private static final String CLIENT_ID = "ed256530fca114cced02";
    private static final String CLIENT_SECRET = "d01124d30845ad2fde655144a3531dc63e0065c3";

    private static final String REDIRECT_URI = "http://127.0.0.1/gitLoginCallback";
    private static final String GITHUB_AUTH_URL_PREFIX = "https://github.com/login/oauth/authorize?";
    private static final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_ACCESS_USER_URL = "https://api.github.com/user";
    private static final String GITHUB_ACCESS_TOKEN_PREFIX = "token ";

    /**
     * <description> 获取github授权地址 </description>
     *
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/12 13:38
     */
    public static String generategitAuthorizeUrl() {
        return GITHUB_AUTH_URL_PREFIX + "client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
    }

    /**
     * <description> 根据传入code,返回github的accessToken </description>
     *
     * @param code :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/12 22:20
     */
    public static String getAccessToken(String code) throws Exception {
        //1.设置请求头 返回参数为json
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Accept", "application/json");
        //2.设置请求体参数
        Map<String, String> parameterMap = new HashMap<>(3);
        parameterMap.put("client_id", CLIENT_ID);
        parameterMap.put("client_secret", CLIENT_SECRET);
        parameterMap.put("code", code);
        //3.响应处理器
        ResponseHandler<String> responseHandler = response -> {
            if (!ResponseUtil.OK_VALUE.equals(response.getStatusLine().getStatusCode())) {
                log.error("根据code获取AccessToken失败{}", response.toString());
                return null;
            }
            String jsonString = EntityUtils.toString(response.getEntity(), WebConstants.DEFAULT_CHARSET);
            GitHubAuthInfo gitHubAuthInfo = JSONObject.parseObject(jsonString, GitHubAuthInfo.class);
            return gitHubAuthInfo.getAccess_token();
        };
        return HttpClientUtil.doPost(GITHUB_ACCESS_TOKEN_URL, headers, parameterMap, responseHandler);
    }

    /**
     * <description> 根据传入AccessToken获取gitHub的用户Id </description>
     *
     * @param accessToken :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/12 22:23
     */
    public static String accessGitHubUserId(String accessToken) throws Exception {
        final JSONObject jsonObject = accessGitHubUserInfo(accessToken);
        return jsonObject.getString("id");
    }

    /**
     * <description> 根据传入AccessToken获取gitHub的用户信息 </description>
     *
     * @param accessToken :
     * @return : com.alibaba.fastjson.JSONObject
     * @author : lw
     * @date : 2020/4/12 22:37
     */
    private static JSONObject accessGitHubUserInfo(String accessToken) throws Exception {
        //1.设置请求头
        Map<String,String> headers = new HashMap<>(1);
        headers.put("Authorization",GITHUB_ACCESS_TOKEN_PREFIX + accessToken);
        ResponseHandler<JSONObject> responseHandler = response -> {
            //2.若响应成功返回 将响应体字符串转换为JSONObject
            if (!ResponseUtil.OK_VALUE.equals(response.getStatusLine().getStatusCode())) {
                log.error("根据code获取AccessToken{}", response.toString());
                return null;
            }
            String jsonString = EntityUtils.toString(response.getEntity(), WebConstants.DEFAULT_CHARSET);
            return JSONObject.parseObject(jsonString);
        };
        return HttpClientUtil.doGet(GITHUB_ACCESS_USER_URL, headers, null, responseHandler);
    }
}
