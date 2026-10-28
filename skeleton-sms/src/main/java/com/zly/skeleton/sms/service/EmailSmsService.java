package com.zly.skeleton.sms.service;

import com.zly.skeleton.sms.entity.EmailSmsMessage;
import com.zly.skeleton.sms.entity.SmsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author ZLY
 * @since 2026/10/28 10:59
 */
@Service("emailSmsService")
@RequiredArgsConstructor
public class EmailSmsService implements SmsService
{

    private final JavaMailSender mailSender;

    @Override
    public void sendSms(SmsMessage smsMessage)
    {
        if (smsMessage instanceof EmailSmsMessage emailSmsMessage) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("zly.nice@qq.com");
            message.setTo(emailSmsMessage.getToEmail());
            message.setSubject(emailSmsMessage.getSubject());
            message.setText(emailSmsMessage.getText());
            mailSender.send(message);
        }
    }
}
