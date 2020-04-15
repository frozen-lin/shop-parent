package com.frozen.pay.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.frozen.mybatis.config.IDataSourceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-03 15:40
 **/
@Configuration
public class DataSourceConfig extends IDataSourceConfig {
    private static final String DATASOURCE_PREFIX = "pay.datasource";

    @Bean
    @ConfigurationProperties(prefix = DATASOURCE_PREFIX)
    @Override
    public DataSource dataSource() {
        return new DruidDataSource();
    }
}
