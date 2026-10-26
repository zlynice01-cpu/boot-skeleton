package com.zly.skeleton.common.security.entity;

import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 登录用户主体，携带 sys_user.id
 * <p>
 * 实现 Serializable：SecurityContext 会被写入 HttpSession，principal 必须可序列化
 *
 * @author ZLY
 */
@Getter
public class LoginUser implements UserDetails, Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，对应 sys_user.id
     */
    private final Long userId;

    /**
     * 所属部门ID，便于后续数据权限使用
     */
    private final Long deptId;

    private final String username;

    private final String password;

    private final Integer status;

    private final Collection<? extends GrantedAuthority> authorities;

    public LoginUser(SysUser user, Collection<? extends GrantedAuthority> authorities)
    {
        this.userId = user.getId();
        this.deptId = user.getDeptId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.authorities = authorities == null ? List.of() : authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 账号是否启用：status == 1 为启用
     */
    @Override
    public boolean isEnabled()
    {
        return Integer.valueOf(1).equals(status);
    }
}
