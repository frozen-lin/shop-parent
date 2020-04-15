package com.frozen.pay.api;

import com.frozen.FrozenCommException;
import com.frozen.pay.bean.PayInfo;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 支付回调服务 </description>
 *
 * @author : lw
 * @date : 2020-04-04 22:35
 **/
@RequestMapping("/pay/callback")
public interface PayCallbackService {
    /**
     * <description> 支付宝支付完成同步回调 </description>
     * @param mapParams : 支付宝参数
     * @return : com.frozen.response.ResponseDataEntity<java.lang.Object>
     * @author : lw
     * @date : 2020/4/7 13:48
     * @throws : FrozenCommException
     */
    @PostMapping("/syncCallBack")
    ResponseDataEntity<PayInfo> syncCallBack(Map<String,String> mapParams) throws FrozenCommException;

    /**
     * <description> 支付宝支付完成异步回调  </description>
     * @param mapParams : 支付宝参数
     * @return : com.frozen.response.ResponseDataEntity<java.lang.Object>
     * @author : lw
     * @date : 2020/4/7 13:48
     */
    @PostMapping("/asyncCallBack")
    ResponseDataEntity<String> asyncCallBack(Map<String,String> mapParams) throws FrozenCommException;
}
