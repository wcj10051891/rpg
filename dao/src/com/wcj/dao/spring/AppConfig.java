package com.wcj.dao.spring;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource(value = "jdbc.properties")
public class AppConfig
{

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource()
    {
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        datasource.setUrl(env.getRequiredProperty("jdbc.url"));
        datasource.setUsername(env.getRequiredProperty("jdbc.username"));
        datasource.setPassword(env.getRequiredProperty("jdbc.password"));
        return datasource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate(dataSource());
    }
}
