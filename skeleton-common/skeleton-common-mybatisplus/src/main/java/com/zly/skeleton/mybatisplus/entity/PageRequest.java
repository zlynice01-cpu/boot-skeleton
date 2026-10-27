package com.zly.skeleton.mybatisplus.entity;

import lombok.Data;

@Data
public class PageRequest {

    /**
     * 页码
     */
    private Long pageNo = 1L;

    /**
     * 页容量
     */
    private Long pageSize = 10L;

    public Long getPageNo() {
        if (pageNo == null || pageNo < 1) {
            return 1L;
        }
        return pageNo;
    }

    public Long getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE) {
            return 10L;
        }
        return pageSize;
    }

}
