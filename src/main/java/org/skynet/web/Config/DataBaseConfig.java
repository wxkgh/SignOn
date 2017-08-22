package org.life.task.Config;

import org.apache.commons.dbcp.BasicDataSource;
import org.life.task.ConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataBaseConfig {
    @Autowired
    ConfigLoader configLoader;

    @Bean
    public BasicDataSource configDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(configLoader.getJdbc_driver());
        dataSource.setUrl(configLoader.getJdbc_url());
        dataSource.setUsername(configLoader.getJdbc_username());
        dataSource.setPassword(configLoader.getJdbc_password());
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        return dataSource;
    }

}
