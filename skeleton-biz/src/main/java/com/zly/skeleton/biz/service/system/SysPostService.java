package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.post.dto.CreatePostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostDTO;
import com.zly.skeleton.biz.domain.post.dto.EditPostStatusDTO;
import com.zly.skeleton.biz.domain.post.dto.PostPageDTO;
import com.zly.skeleton.biz.domain.post.vo.PostPageItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysPost;

/**
 * 岗位服务
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
public interface SysPostService extends IService<SysPost>
{
    Result<PageResponse<PostPageItemVO>> getPostPage(PostPageDTO dto);

    void create(CreatePostDTO dto);

    void edit(EditPostDTO dto);

    void updateStatus(EditPostStatusDTO dto);

    void removePost(Long id);

}
