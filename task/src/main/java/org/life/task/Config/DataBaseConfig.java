package org.life.task.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class dataBaseConfig {
    @Value("${jdbc.driver}")
    private String jdbc_driver;

    @Value("${jdbc.url}")
    private String jdbc_url;

    @Value("${jdbc.username}")
    private String jdbc_username;

    @Value("${jdbc.password}")
    private String jdbc_password;

    public String getJdbc_driver() {
        return jdbc_driver;
    }

    public String getJdbc_url() {
        return jdbc_url;
    }

    public String getJdbc_username() {
        return jdbc_username;
    }

    public String getJdbc_password() {
        return jdbc_password;
    }
}
