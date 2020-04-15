package com.frozen.pay.api;

import com.frozen.FrozenCommException;
import com.frozen.pay.bean.PaymentEntity;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <program> shop-parent </program>
 * <description> 支付服务接口 </description>
 *
 * @author : lw
 * @date : 2020-04-04 21:49
 **/
@RequestMapping("/pay")
public interface PayService {
    /**
     * <description> 根据传入的payToken创建跳转支付宝form表单 </description>
     * @param payToken :
     * @return : com.frozen.response.ResponseDataEntity<java.lang.String> 返回页面form表单
     * @author : lw
     * @date : 2020/4/4 21:56
     */
    @PostMapping("/getAlipayPage")
    ResponseDataEntity<String> getAlipayPage(@RequestParam("payToken") String payToken);

    /**
     * <description> 根据传入支付实体类创建payment对象 </description>
     * @param paymentEntity : 支付实体类 : 需要传入 userId price
     * @return : com.frozen.response.ResponseDataEntity<java.lang.String> 返回payToken
     * @author : lw
     * @date : 2020/4/6 9:10
     */
    @PostMapping("/createPay")
    ResponseDataEntity<String> createPay(@RequestBody PaymentEntity paymentEntity) throws FrozenCommException;

}
