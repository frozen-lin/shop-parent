package com.frozen.member.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.frozen.mybatis.config.IDataSourceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * <program> shopparent </program>
 * <description> 数据库连接池配置类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 09:47
 **/
@Configuration
public class DataSourceConfig extends IDataSourceConfig {
    private static final String DATASOURCE_PREFIX = "member.datasource";
    @Bean
    @Primary
    @ConfigurationProperties(prefix = DATASOURCE_PREFIX)
    @Override
    public DataSource dataSource() {
        return new DruidDataSource();
    }
}
