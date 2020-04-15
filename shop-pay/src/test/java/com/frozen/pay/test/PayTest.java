package com.frozen.pay.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.frozen.pay.bean.AlipayBizContent;
import com.frozen.pay.comm.PayConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-04 13:58
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PayTest {
    @Autowired
    private AlipayClient alipayClient;
    @Test
    public void testAliPay() throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayBizContent alipayBizContent = new AlipayBizContent();
        alipayBizContent.setOperator_id("user1");
        alipayBizContent.setOut_trade_no("123456");
        alipayBizContent.setSubject("测试商品");
        alipayBizContent.setTotal_amount("99.99");
        request.setBizContent(alipayBizContent.toString());
        request.setNotifyUrl(PayConstants.notifyUrl);
        request.setReturnUrl(PayConstants.returnUrl);
        String body = alipayClient.pageExecute(request).getBody();
        log.info(body);
    }
}
