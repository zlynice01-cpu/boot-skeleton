package com.zly.skeleton.mybatisplus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author ZLY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private Long pageNo = 1L;

    private Long pageSize = 10L;

    private Long total = 0L;

    private Long totalPages = 0L;

    private List<T> result = Collections.emptyList();

    private Long start = 1L;

    private Long end = 0L;

    public void setResult(List<T> result) {
        this.result = result;
        if (result != null && !result.isEmpty() && this.total == 0) {
            setTotal((long) result.size());
        }
    }

    public void setCurrent(Long pageNo) {
        if (pageNo != null && pageNo > 0) {
            this.pageNo = pageNo;
        }
    }

    public void setPageSize(Long pageSize) {
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public void setTotal(Long total) {
        this.total = total;
        if (total == -1) {
            this.pageNo = 1L;
            return;
        }
        if (this.pageSize > 0) {
            this.totalPages = (total / this.pageSize) + (total % this.pageSize == 0 ? 0 : 1);
        } else {
            this.totalPages = 0L;
        }

        this.start = (this.pageNo > 0 ? (this.pageNo - 1) * this.pageSize : 0) + 1;
        if (this.pageNo > 0) {
            this.end = this.pageNo * this.pageSize;
            // 不超过总记录数
            this.end = Math.min(this.end, this.total);
        } else {
            // pageNo <= 0 时，默认查第一页，或者设为 total，避免 start > end
            this.end = Math.min(this.pageSize, this.total);
        }
    }

}
