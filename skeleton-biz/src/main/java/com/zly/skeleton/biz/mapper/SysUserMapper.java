package com.zly.skeleton.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zly.skeleton.biz.domain.user.dto.UserPageDTO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:17
 */
public interface SysUserMapper extends BaseMapper<SysUser>
{

    IPage<UserPageItemVO> selectUserPage(@Param("page") IPage<UserPageItemVO> page, @Param("dto") UserPageDTO dto);

}
