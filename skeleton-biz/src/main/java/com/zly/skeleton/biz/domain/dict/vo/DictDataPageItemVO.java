package com.zly.skeleton.biz.domain.dict.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysDictData;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 字典数据分页列表项
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DictDataPageItemVO
{
    // 字典数据id
    private Long id;

    // 字典类型id
    private Long dictTypeId;

    // 字典编码
    private String dictCode;

    // 字典标签
    private String dictLabel;

    // 字典值
    private String dictValue;

    // 自定义css样式
    private String cssClass;

    // 前端标签类型 success/danger/warning
    private String listClass;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

    // 备注
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static DictDataPageItemVO entityToVO(SysDictData dictData) {
        if (dictData == null)
        {
            return null;
        }
        DictDataPageItemVO vo = new DictDataPageItemVO();
        BeanUtils.copyProperties(dictData, vo);
        return vo;
    }
}
