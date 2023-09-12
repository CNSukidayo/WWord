package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Post;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/9/12 19:12
 */
@Repository
public interface PostMapper extends BaseMapper<Post> {
}
