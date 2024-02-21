package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.Post;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.param.PublishPostParam;
import io.github.cnsukidayo.wword.model.vo.PostAbstractVO;
import io.github.cnsukidayo.wword.model.vo.PostDetailVo;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/12 18:42
 */
public interface PostService extends IService<Post> {
    /**
     * 发布一个帖子
     *
     * @param publishPostParam 发布一个帖子
     * @param user             发布帖子的用户
     */
    void publishPost(PublishPostParam publishPostParam, User user);

    /**
     * 获得帖子的摘要信息列表
     *
     * @return 返回值不为null
     */
    List<PostAbstractVO> getPostListUncheck();

    /**
     * 获取帖子的详情信息
     *
     * @param id 用户的id
     * @return 返回值不为null
     */
    PostDetailVo getPostDetailUncheck(Long id);
}
