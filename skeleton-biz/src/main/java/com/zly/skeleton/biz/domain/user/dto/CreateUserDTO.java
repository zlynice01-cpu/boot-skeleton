package com.zly.skeleton.biz.domain.user.dto;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zly.skeleton.common.security.config.MyBCryptPasswordEncoder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 11:17
 */
@Data
public class CreateUserDTO
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

    private List<Long> roleIds;

    // 部门id
    @NotNull(message = "缺少部门信息")
    private Long deptId;

    public void encryptPassword(String defPassword)
    {
        if (StringUtils.isBlank(this.password) && StringUtils.isBlank(defPassword))
        {
            this.password = "123456";
        }
        if (StringUtils.isBlank(this.password))
        {
            this.password = defPassword;
        }
        MyBCryptPasswordEncoder encoder = new MyBCryptPasswordEncoder();
        this.password = encoder.encode(this.password);
    }

    public void checkStatus()
    {
        if (this.status == null)
        {
            this.status = 0;
        }
    }

    public void checkAvatar()
    {
        if (StringUtils.isNotBlank(this.avatar))
        {
            return;
        }
        this.avatar = "default_avatar.jpg";
    }

    public void initAndCheck(String defPassword)
    {
        encryptPassword(defPassword);
        checkAvatar();
        checkStatus();
    }
}
