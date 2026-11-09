package com.zly.skeleton.core.controller.company;

import com.zly.skeleton.biz.domain.post.dto.CreatePostDTO;
import com.zly.skeleton.biz.domain.post.dto.DeletePostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostStatusDTO;
import com.zly.skeleton.biz.domain.post.dto.PostPageDTO;
import com.zly.skeleton.biz.domain.post.vo.PostPageItemVO;
import com.zly.skeleton.biz.service.system.SysPostService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位管理
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class SysPostController
{
    private final SysPostService postService;

    @PostMapping("/page")
    public Result<PageResponse<PostPageItemVO>> page(@Valid @RequestBody PostPageDTO dto) {
        return postService.getPostPage(dto);
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreatePostDTO dto) {
        postService.create(dto);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditPostDTO dto) {
        postService.edit(dto);
        return Result.success();
    }

    @PostMapping("/status")
    public Result<Integer> updateStatus(@Valid @RequestBody EditPostStatusDTO dto) {
        return postService.enable(dto);
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeletePostDTO dto) {
        postService.removePost(dto.getId());
        return Result.success();
    }

}
