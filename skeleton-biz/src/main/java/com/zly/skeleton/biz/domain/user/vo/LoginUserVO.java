package com.zly.skeleton.biz.domain.user.vo;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zly.skeleton.biz.domain.dept.vo.DeptProfileVO;
import com.zly.skeleton.biz.domain.promission.vo.MenusVO;
import com.zly.skeleton.biz.domain.role.vo.RoleProfileVO;
import com.zly.skeleton.common.util.TreeUtil;
import com.zly.skeleton.mybatisplus.entity.SysDept;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:28
 */
@Data
public class LoginUserVO
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
    private DeptProfileVO deptInfo;

    // 角色列表
    private List<RoleProfileVO> roleList;

    // 权限列表
    private List<String> permissions;

    private List<MenusVO> menus;

    public static LoginUserVO createVO(SysUser sysUser, List<SysRole> roleList, List<SysPermission> permissionList, SysDept sysDept)
    {
        LoginUserVO vo = new LoginUserVO();
        if (sysUser != null)
        {
            BeanUtils.copyProperties(sysUser, vo);
        }

        List<RoleProfileVO> roles = List.of();
        List<MenusVO> menus = List.of();
        List<String> permissions = List.of();

        if (ObjectUtils.isNotEmpty(roleList))
        {
            roles = roleList.stream().filter(Objects::nonNull)
                    .map(item -> {
                        RoleProfileVO roleVO = new RoleProfileVO();
                        BeanUtils.copyProperties(item, roleVO);
                        return roleVO;
                    }).toList();
        }

        if (ObjectUtils.isNotEmpty(permissionList))
        {
            menus = permissionList.stream()
                    .filter(Objects::nonNull)
                    .map(item -> {
                        MenusVO promissionVO = new MenusVO();
                        BeanUtils.copyProperties(item, promissionVO);
                        return promissionVO;
                    })
                    .toList();
            menus = TreeUtil.buildTree(menus, "0");

            permissions = permissionList.stream()
                    .filter(Objects::nonNull)
                    .map(SysPermission::getPermissionKey)
                    .filter(StringUtils::isNotBlank)
                    .toList();
        }

        if (sysDept != null) {
            DeptProfileVO deptVO = new DeptProfileVO();
            BeanUtils.copyProperties(sysDept, deptVO);
            vo.setDeptInfo(deptVO);
        }

        vo.setRoleList(roles);
        vo.setMenus(menus);
        vo.setPermissions(permissions);

        return vo;
    }

}
