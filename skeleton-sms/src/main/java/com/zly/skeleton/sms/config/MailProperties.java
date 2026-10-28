package com.zly.skeleton.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 *
 * @author ZLY
 * @since 2026/10/28 11:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties
{

    private String host = "localhost";

    private int port = 25;

    private String username;

    private String password;

    private String protocol = "smtp";

    private String defaultEncoding = "UTF-8";

    private boolean auth;

    private boolean starttlsEnable;

    private final Properties properties = new Properties();

}
