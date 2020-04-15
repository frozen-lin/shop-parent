package com.frozen.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <program> shop-parent </program>
 * <description> 阿里pay支付config </description>
 *
 * @author : lw
 * @date : 2020-04-04 12:54
 **/
@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayConfig {
    private String gatewayUrl;
    private String appId;
    private String format;
    private String merchantPrivateKey;
    private String alipayPublicKey;
    private String charset;
    private String signType;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(this.gatewayUrl, this.appId, this.merchantPrivateKey, this.format, this.charset, this.alipayPublicKey, this.signType);
    }
}
