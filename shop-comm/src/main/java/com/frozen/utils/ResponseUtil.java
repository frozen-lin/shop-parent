package com.frozen.utils;


import com.frozen.response.ResponseDataEntity;
import org.springframework.http.HttpStatus;

/**
 * <program> shopparent </program>
 * <description> 接口返回实体工具类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 14:06
 **/
public class ResponseUtil {
    public static Integer OK_VALUE = HttpStatus.OK.value();
    /**
     * <description> 判断返回相应数据是否成功 </description>
     * @param responseDataEntity :
     * @return : boolean
     * @author : lw
     * @date : 2020/4/11 15:29
     */
    public static boolean checkResponseOk(ResponseDataEntity<?> responseDataEntity) {
        if (responseDataEntity == null) {
            return false;
        }
        return ResponseUtil.OK_VALUE.equals(responseDataEntity.getResCode());
    }

    /**
     * <description> 设置并获取ResponseDataEntity </description>
     *
     * @param resCode : 响应码
     * @param resMsg  : 响应信息
     * @param data    :  响应数据实体类
     * @return : com.frozen.Response.ResponseDataEntity<T>
     * @author : lw
     * @date : 2020/3/25 14:15
     */
    public static <T> ResponseDataEntity<T> getResponse(Integer resCode, String resMsg, T data) {
        return new ResponseDataEntity<>(resCode, resMsg, data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,resMsg:success,data:null </description>
     *
     * @return : com.frozen.Response.ResponseDataEntity<java.lang.Object>
     * @author : lw
     * @date : 2020/3/25 14:15
     */
    public static ResponseDataEntity<Object> getResponseSuccess() {
        return getResponseSuccess(null);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,resMsg:success,自定义实体类 </description>
     *
     * @param data :响应数据实体类
     * @return : com.frozen.Response.ResponseDataEntity<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataEntity<T> getResponseSuccess(T data) {
        return getResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:200,自定义信息和实体类 </description>
     *
     * @param resMsg :响应信息
     * @param data   :响应数据实体类
     * @return : com.frozen.Response.ResponseDataEntity<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataEntity<T> getResponseSuccess(String resMsg, T data) {
        return getResponse(HttpStatus.OK.value(), resMsg, data);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:500,resMsg:Internal Server Error,data:null </description>
     *
     * @return : com.frozen.Response.ResponseDataEntity<java.lang.Object>
     * @author : lw
     * @date : 2020/3/25 14:15
     */
    public static ResponseDataEntity<Object> getResponseError() {
        return getResponseError(null);
    }

    /**
     * <description> 获取通用成功ResponseDataEntity,resCode:500,resMsg:Internal Server Error,自定义实体类 </description>
     *
     * @param data :响应数据实体类
     * @return : com.frozen.Response.ResponseDataEntity<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataEntity<T> getResponseError(T data) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), data);
    }

    /**
     * <description> 获取通用失败ResponseDataEntity,resCode:500,自定义信息和实体类 </description>
     *
     * @param resMsg :响应信息
     * @param data   :响应数据实体类
     * @return : com.frozen.Response.ResponseDataEntity<T>
     * @author : lw
     * @date : 2020/3/25 14:16
     */
    public static <T> ResponseDataEntity<T> getResponseError(String resMsg, T data) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), resMsg, data);
    }


}
