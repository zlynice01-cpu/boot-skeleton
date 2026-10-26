package com.zly.skeleton.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:04
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission>
{

    List<SysPermission> selectPermissionsByRoleIds(@Param("roleIdSet") Collection<Long> roleIdSet);

}
    