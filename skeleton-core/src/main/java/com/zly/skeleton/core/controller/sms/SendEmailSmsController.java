package com.zly.skeleton.core.controller.sms;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.sms.entity.EmailSmsMessage;
import com.zly.skeleton.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ZLY
 * @since 2026/10/28 11:37
 */
@RestController
@RequestMapping("/sms/email")
@RequiredArgsConstructor
public class SendEmailSmsController
{
    private final SmsService smsService;

    @PostMapping("/send")
    public Result<Void> send(@RequestBody EmailSmsMessage message) {
        smsService.sendSms(message);
        return Result.success();
    }

}
