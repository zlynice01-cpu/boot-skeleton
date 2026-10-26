package com.zly.skeleton.common.security.entity.user.dto;

import ch.qos.logback.core.util.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zly.skeleton.common.security.config.MyBCryptPasswordEncoder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/26 11:24
 */
@Data
public class SeCreateUserDTO
{

    // 用户名
    @NotEmpty(message = "缺少用户名")
    private String username;

    // 密码
    private String password;

    // 昵称
    @NotEmpty(message = "缺少昵称")
    private String nickname;

    // 邮箱
    private String email;

    // 电话号
    private String phone;

    // 头像
    private String avatar;

    // 状态 0禁用 1正常
    private Integer status;

    // 部门id
    @NotNull(message = "缺少部门信息")
    private Long deptId;

    public void encryptPassword()
    {
        if (StringUtils.isBlank(this.password)) {
            this.password = "123456";
        }
        MyBCryptPasswordEncoder encoder = new MyBCryptPasswordEncoder();
        this.password = encoder.encode(this.password);
    }

    public void checkStatus()
    {
        if (this.status == null) {
            this.status =  0;
        }
    }

    public void checkAvatar()
    {
        if (StringUtils.isNotBlank(this.avatar)) {
            return;
        }
        this.avatar = "default_avatar.jpg";
    }

    public void initAndCheck() {
        encryptPassword();
        checkAvatar();
        checkAvatar();
    }

}
