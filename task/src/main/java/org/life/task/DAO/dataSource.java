package org.life.task.DAO;

import org.apache.commons.dbcp.BasicDataSource;
import org.life.task.Config.DataBaseConfig;
import org.springframework.context.annotation.Bean;

public class dataSource {
    @Bean
    public BasicDataSource newDataSource() {
        BasicDataSource ds = new BasicDataSource();
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        ds.setDriverClassName(dataBaseConfig.getJdbc_driver());
        ds.setUrl(dataBaseConfig.getJdbc_url());
        ds.setUsername(dataBaseConfig.getJdbc_username());
        ds.setPassword(dataBaseConfig.getJdbc_password());
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }
}
