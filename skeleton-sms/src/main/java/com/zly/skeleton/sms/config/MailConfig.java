package com.zly.skeleton.sms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件发送配置。
 * <p>
 * Spring Boot 的 {@code MailSenderAutoConfiguration} 依赖 {@code spring.mail.host} 才会创建
 * {@link JavaMailSender}，未配置邮件服务器时容器中不存在该 Bean。这里在缺失时兜底创建，
 * 保证应用可以正常启动，仅在真正发送邮件时才会因缺少有效配置而失败。
 *
 * @author ZLY
 * @since 2026/10/28 11:30
 */
@Configuration
@RequiredArgsConstructor
public class MailConfig
{

    /**
     * 仅在 Spring Boot 邮件自动配置未生效（容器中没有 JavaMailSender）时生效。
     */
    @Bean
    @ConditionalOnMissingBean(JavaMailSender.class)
    public JavaMailSender javaMailSender(MailProperties mailProperties)
    {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(mailProperties.getHost());
        sender.setPort(mailProperties.getPort());
        sender.setUsername(mailProperties.getUsername());
        sender.setPassword(mailProperties.getPassword());
        sender.setDefaultEncoding(mailProperties.getDefaultEncoding());

        if (mailProperties.getProtocol() != null) {
            sender.setProtocol(mailProperties.getProtocol());
        }

        Properties javaMailProperties = sender.getJavaMailProperties();
        javaMailProperties.putAll(mailProperties.getProperties());
        if (mailProperties.isAuth()) {
            javaMailProperties.put("mail.smtp.auth", "true");
        }
        if (mailProperties.isStarttlsEnable()) {
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
        }

        return sender;
    }

}
