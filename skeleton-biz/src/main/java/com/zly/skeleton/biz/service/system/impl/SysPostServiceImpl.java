package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.post.dto.CreatePostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostStatusDTO;
import com.zly.skeleton.biz.domain.post.dto.PostPageDTO;
import com.zly.skeleton.biz.domain.post.vo.PostPageItemVO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.mapper.SysPostMapper;
import com.zly.skeleton.biz.service.system.SysPostService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysPost;
import com.zly.skeleton.mybatisplus.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 岗位服务实现
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService
{
    private final SysPostMapper postMapper;

    @Override
    public Result<PageResponse<PostPageItemVO>> getPostPage(PostPageDTO dto)
    {
        String postCode = dto.getPostCode();
        String postName = dto.getPostName();
        Integer status = dto.getStatus();

        IPage<SysPost> page = PageUtil.createPage(dto);
        IPage<PostPageItemVO> voPage = postMapper
                .selectPage(page, Wrappers.lambdaQuery(SysPost.class)
                        .like(StringUtils.isNotBlank(postCode), SysPost::getPostCode, postCode)
                        .like(StringUtils.isNotBlank(postName), SysPost::getPostName, postName)
                        .eq(status != null, SysPost::getStatus, status)
                        .orderByAsc(SysPost::getSort)
                        .orderByAsc(SysPost::getId))
                .convert(PostPageItemVO::entityToVO);

        PageResponse<PostPageItemVO> voResult = PageUtil.getPageResult(voPage);

        return Result.success(voResult);
    }

    @Override
    public void create(CreatePostDTO dto)
    {
        checkPostCode(dto.getPostCode(), null);

        SysPost post = new SysPost();
        BeanUtils.copyProperties(dto, post);
        if (post.getStatus() == null)
        {
            post.setStatus(StatusEnum.YES.getCode());
        }
        if (post.getSort() == null)
        {
            post.setSort(0);
        }
        save(post);
    }

    @Override
    public void edit(EditPostDTO dto)
    {
        checkPostExists(dto.getId());
        checkPostCode(dto.getPostCode(), dto.getId());

        SysPost post = new SysPost();
        BeanUtils.copyProperties(dto, post);
        updateById(post);
    }

    @Override
    public void removePost(Long id)
    {
        checkPostExists(id);
        removeById(id);
    }

    @Override
    public Result<Integer> enable(EditPostStatusDTO dto)
    {
        SysPost post = checkPostExists(dto.getId());
        Integer status = post.getStatus();
        boolean enable = StatusEnum.YES.getCode().equals(status);
        Integer nextStatus = enable ? StatusEnum.NO.getCode() : StatusEnum.YES.getCode();
        post.setStatus(nextStatus);
        updateById(post);
        return Result.success(nextStatus, enable ? "禁用成功" : "启用成功");
    }

    private SysPost checkPostExists(Long id)
    {
        SysPost post = getById(id);
        if (post == null)
        {
            throw new BizException(BizErrorEnum.POST_NOT_FOUND);
        }
        return post;
    }

    /**
     * 校验岗位编码唯一，id 不为空时排除自身
     */
    private void checkPostCode(String postCode, Long id)
    {
        List<SysPost> posts = postMapper.selectList(Wrappers.lambdaQuery(SysPost.class)
                .eq(SysPost::getPostCode, postCode));
        if (ObjectUtils.isEmpty(posts))
        {
            return;
        }
        boolean conflict = posts.stream().filter(Objects::nonNull)
                .anyMatch(item -> !item.getId().equals(id));
        if (conflict)
        {
            throw new BizException(BizErrorEnum.POST_CODE_EXIST);
        }
    }
}
