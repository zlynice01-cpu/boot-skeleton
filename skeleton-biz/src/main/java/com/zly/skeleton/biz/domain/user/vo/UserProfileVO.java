package com.zly.skeleton.biz.domain.user.vo;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zly.skeleton.biz.domain.dept.vo.DeptProfileVO;
import com.zly.skeleton.biz.domain.promission.vo.MenusVO;
import com.zly.skeleton.biz.domain.role.vo.RoleProfileVO;
import com.zly.skeleton.common.util.TreeUtil;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:28
 */
@Data
public class UserProfileVO
{

    // 用户id
    private Long id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 邮箱
    private String email;

    // 电话号
    private String phone;

    // 头像
    private String avatar;

    // 状态
    private Integer status;

    // 部门id
    private Long deptId;

    // 角色id列表
    private Collection<Long> roleIds;

}
