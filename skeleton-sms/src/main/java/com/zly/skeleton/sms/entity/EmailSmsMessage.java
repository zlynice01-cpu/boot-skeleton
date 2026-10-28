package com.zly.skeleton.sms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ZLY
 * @since 2026/10/28 11:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailSmsMessage extends SmsMessage
{

    private String toEmail;

    private String subject;

}
