package com.zly.skeleton.mybatisplus.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zly.skeleton.mybatisplus.entity.PageRequest;
import com.zly.skeleton.mybatisplus.entity.PageResponse;

/**
 *
 * @author ZLY
 * @since 2026/10/27 16:08
 */
public class PageUtil
{
    public static <T> IPage<T> createPage(PageRequest pageRequest) {
        return new Page<>(pageRequest.getPageNo(), pageRequest.getPageSize());
    }

    public static <T> PageResponse<T> getPageResult(IPage<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(page.getCurrent());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setResult(page.getRecords());
        pageResponse.setTotal(page.getTotal());
        return pageResponse;
    }

}
