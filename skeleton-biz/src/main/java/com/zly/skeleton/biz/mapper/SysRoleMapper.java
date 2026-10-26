package com.zly.skeleton.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:35
 */
public interface SysRoleMapper extends BaseMapper<SysRole>
{

    List<SysRole> selectRoleListByUserId(@Param("userId") Long userId);

}
    