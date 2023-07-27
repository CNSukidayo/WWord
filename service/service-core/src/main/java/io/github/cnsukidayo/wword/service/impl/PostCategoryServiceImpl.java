package io.github.cnsukidayo.wword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.dao.PostCategoryMapper;
import io.github.cnsukidayo.wword.model.enums.PublicNess;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import io.github.cnsukidayo.wword.service.PostCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/27 10:43
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory> implements PostCategoryService {


    @Override
    public void save(AddPostCategoryParam addPostCategoryParam, Long UUID) {
        Assert.notNull(addPostCategoryParam, "AddPostCategoryParam must not be null");
        Assert.notNull(UUID, "UUID must not be null");

        PostCategory postCategory = new PostCategory();
        BeanUtils.copyProperties(addPostCategoryParam, postCategory);
        postCategory.setUUID(UUID);

        baseMapper.insert(postCategory);
    }

    @Override
    public List<PostCategory> list(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        return Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getUUID, uuid))).orElseGet(ArrayList::new);
    }

    @Override
    public List<PostCategory> listPublic(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        return list(uuid).stream()
                .filter(postCategory -> postCategory.getPublicNess() == PublicNess.PUBLIC)
                .collect(Collectors.toList());
    }

    @Override
    public void updateByUUID(UpdatePostCategoryParam updatePostCategoryParam, Long uuid) {
        Assert.notNull(updatePostCategoryParam, "updatePostCategoryParam must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        PostCategory postCategory = new PostCategory();
        BeanUtils.copyProperties(updatePostCategoryParam, postCategory);
        postCategory.setUUID(uuid);

        baseMapper.update(postCategory, new LambdaQueryWrapper<PostCategory>()
                .eq(PostCategory::getId, updatePostCategoryParam.getId())
                .eq(PostCategory::getUUID, uuid));
    }

    @Override
    public void removeById(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        baseMapper.delete(new LambdaQueryWrapper<PostCategory>()
                .eq(PostCategory::getId, id)
                .eq(PostCategory::getUUID, uuid));
    }

    @Override
    public void like(Long id, Long uuid) {

    }


}
