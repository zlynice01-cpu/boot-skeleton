package com.zly.skeleton.biz.domain.dict.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysDictType;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 字典类型分页列表项
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DictTypePageItemVO
{
    // 字典类型id
    private Long id;

    // 字典编码
    private String dictCode;

    // 字典名称
    private String dictName;

    // 状态 0禁用 1正常
    private Integer status;

    // 备注
    private String remark;

    // 排序号
    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static DictTypePageItemVO entityToVO(SysDictType dictType) {
        if (dictType == null)
        {
            return null;
        }
        DictTypePageItemVO vo = new DictTypePageItemVO();
        BeanUtils.copyProperties(dictType, vo);
        return vo;
    }
}
