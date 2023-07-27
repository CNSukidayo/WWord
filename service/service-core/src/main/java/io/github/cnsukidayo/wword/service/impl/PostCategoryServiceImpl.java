package io.github.cnsukidayo.wword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.dao.PostCategoryMapper;
import io.github.cnsukidayo.wword.exception.AuthenticationException;
import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import io.github.cnsukidayo.wword.service.PostCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        // 查询到当前用户的所有收藏夹,未处理引用收藏夹
        List<PostCategory> postCategoryList = Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getUUID, uuid))).orElseGet(ArrayList::new);
        // 查询到所有的引用收藏夹,并且按照K-V封装 K是收藏夹的id,value是未处理的收藏夹
        Map<Long, PostCategory> linkMap = postCategoryList.stream()
                .filter(postCategory -> postCategory.getCategoryAttribute() == CategoryAttribute.LINK)
                .collect(Collectors.toMap(PostCategory::getId, postCategory -> postCategory));
        // 拿到所有的收藏夹id,从数据库中查询到所有被引用的真实收藏夹的信息.并将内容复制给引用收藏夹
        Optional.ofNullable(baseMapper.selectTargetPostCategory(linkMap.keySet().stream().toList())).orElseGet(ArrayList::new)
                .forEach(postCategory -> {
                    PostCategory linkPostCategory = linkMap.get(postCategory.getId());
                    BeanUtils.copyProperties(postCategory, linkPostCategory, "uuid");
                });

        return postCategoryList;
    }

    @Override
    public List<PostCategory> listPublic(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        return list(uuid).stream()
                .filter(postCategory -> postCategory.getCategoryAttribute() == CategoryAttribute.PUBLIC)
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
                .eq(PostCategory::getUUID, uuid)
                .ne(PostCategory::getCategoryAttribute, CategoryAttribute.LINK));
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
    public Boolean like(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");
        // todo 后序考虑使用mq来异步处理点赞
        return baseMapper.insertLike(id, uuid) > 0;
    }

    @Override
    public Boolean dislike(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        return baseMapper.deleteLike(id, uuid) > 0;
    }

    @Override
    public Integer likeCount(Long id) {
        Assert.notNull(id, "id must not be null");

        return baseMapper.likeCount(id);
    }

    @Override
    public PostCategory getById(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        // 首先查询到该收藏夹
        PostCategory postCategory = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id)
                        .eq(PostCategory::getUUID, uuid)))
                .orElseGet(PostCategory::new);
        // 如果收藏夹是私密的,并且不是用户本人访问的话,则抛出异常
        if (postCategory.getCategoryAttribute() == CategoryAttribute.PRIVATE && !postCategory.getUUID().equals(uuid))
            throw new AuthenticationException("您没有权限访问该内容!");
        // 如果查询到的收藏夹是链接收藏夹,则查询真实收藏夹信息
        if (postCategory.getCategoryAttribute() == CategoryAttribute.LINK) {
            postCategory = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id)
                            .eq(PostCategory::getCategoryAttribute, CategoryAttribute.PUBLIC)))
                    .orElse(postCategory);
            // 依旧告诉前端这是一个链接收藏夹
            postCategory.setCategoryAttribute(CategoryAttribute.PRIVATE);
        }

        return postCategory;
    }


}
