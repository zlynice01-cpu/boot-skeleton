package com.zly.skeleton.common.security.entity;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/24 11:11
 */
@Data
public class LoginParamDTO
{

    private String username;

    private String password;

    private String code;

}
