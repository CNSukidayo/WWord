package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.core.dao.PostCategoryMapper;
import io.github.cnsukidayo.wword.core.service.PostCategoryService;
import io.github.cnsukidayo.wword.core.service.UserService;
import io.github.cnsukidayo.wword.model.entity.PostCategory;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.enums.CategoryAttribute;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.AddPostCategoryParam;
import io.github.cnsukidayo.wword.model.params.UpdatePostCategoryParam;
import io.github.cnsukidayo.wword.model.vo.PostCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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

    private final UserService userService;

    public PostCategoryServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void save(AddPostCategoryParam addPostCategoryParam, Long UUID) {
        Assert.notNull(addPostCategoryParam, "AddPostCategoryParam must not be null");
        Assert.notNull(UUID, "UUID must not be null");

        PostCategory postCategory = addPostCategoryParam.convertTo();
        postCategory.setUuid(UUID);

        baseMapper.insert(postCategory);
    }

    @Override
    public List<PostCategory> list(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        // 查询到当前用户的所有收藏夹,未处理引用收藏夹
        List<PostCategory> postCategoryList = Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getUuid, uuid))).orElseGet(ArrayList::new);
        // 查询到所有的引用收藏夹,并且按照K-V封装 K是收藏夹的id,value是未处理的收藏夹
        Map<Long, PostCategory> linkMap = postCategoryList.stream()
            .filter(postCategory -> postCategory.getCategoryAttribute() == CategoryAttribute.LINK)
            .collect(Collectors.toMap(PostCategory::getId, postCategory -> postCategory));
        // 拿到所有的收藏夹id,从数据库中查询到所有被引用的真实收藏夹的信息.并将内容复制给引用收藏夹
        List<Long> linkIdList = new ArrayList<>(linkMap.keySet());
        // 拿到所有收藏夹对应的用户id
        List<Long> UUIDList = new ArrayList<>(linkIdList.size() + 1);
        if (!CollectionUtils.isEmpty(linkIdList)) {
            Optional.ofNullable(baseMapper.selectTargetPostCategory(linkIdList)).orElseGet(ArrayList::new)
                .forEach(postCategory -> {
                    UUIDList.add(postCategory.getUuid());
                    PostCategory linkPostCategory = linkMap.get(postCategory.getId());
                    // 注意在展示列表用户拿到的还是链接收藏夹
                    BeanUtils.copyProperties(postCategory, linkPostCategory, "categoryAttribute");
                });
        }
        // 查询收藏夹的创建者信息
        UUIDList.add(uuid);
        Map<Long, User> userMap = userService.listByIds(UUIDList).stream().collect(Collectors.toMap(User::getUuid, user -> user));
        // 更新收藏夹的创建者信息
        postCategoryList.forEach(postCategory -> postCategory.setCreateName(userMap.get(postCategory.getUuid()).getNick()));
        return postCategoryList;
    }

    @Override
    public List<PostCategory> listPublic(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        return list(uuid).stream()
            .filter(postCategory -> postCategory.getCategoryAttribute() != CategoryAttribute.PRIVATE)
            .collect(Collectors.toList());
    }

    @Override
    public void updateByUUID(UpdatePostCategoryParam updatePostCategoryParam, Long uuid) {
        Assert.notNull(updatePostCategoryParam, "updatePostCategoryParam must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        PostCategory postCategory = updatePostCategoryParam.convertTo();
        postCategory.setUuid(uuid);

        baseMapper.update(postCategory, new LambdaQueryWrapper<PostCategory>()
            .eq(PostCategory::getId, updatePostCategoryParam.getId())
            .eq(PostCategory::getUuid, uuid)
            .ne(PostCategory::getCategoryAttribute, CategoryAttribute.LINK));
    }

    @Override
    public void removeById(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        baseMapper.delete(new LambdaQueryWrapper<PostCategory>()
            .eq(PostCategory::getId, id)
            .eq(PostCategory::getUuid, uuid));
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
    public PostCategoryVO getById(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        // 首先查询到该收藏夹
        PostCategory postCategory = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id)
                .eq(PostCategory::getUuid, uuid)))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS, "未知收藏夹"));
        // 如果收藏夹是私密的,并且不是用户本人访问的话,则抛出异常
        if (postCategory.getCategoryAttribute() == CategoryAttribute.PRIVATE && !postCategory.getUuid().equals(uuid))
            throw new BadRequestException(ResultCodeEnum.AUTHENTICATION);
        // 如果查询到的收藏夹是链接收藏夹,则查询真实收藏夹信息
        if (postCategory.getCategoryAttribute() == CategoryAttribute.LINK) {
            postCategory = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id)
                    .eq(PostCategory::getCategoryAttribute, CategoryAttribute.PUBLIC)))
                .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS,"收藏夹不存在或已被删除"));
            // 依旧告诉前端这是一个链接收藏夹
            postCategory.setCategoryAttribute(CategoryAttribute.LINK);
        }
        // 封装收藏夹创建者信息
        postCategory.setCreateName(userService.getById(postCategory.getUuid()).getNick());
        PostCategoryVO postCategoryVO = new PostCategoryVO().convertFrom(postCategory);
        // 查询收藏夹的点赞数
        postCategoryVO.setLikeCount(baseMapper.likeCount(id));
        // 查询收藏夹的被收藏数
        postCategoryVO.setStarCount(baseMapper.selectCount(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id)) - 1);
        // 查询收藏夹的内容

        return postCategoryVO;
    }

    @Override
    public void star(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        // 查询目标收藏夹
        PostCategory targetPostCategory = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<PostCategory>().eq(PostCategory::getId, id).eq(PostCategory::getCategoryAttribute, CategoryAttribute.PUBLIC)))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS,
                "目标收藏夹不存在!"));
        // 自已不能收藏自已的收藏夹
        if (targetPostCategory.getUuid().equals(uuid)) {
            throw new BadRequestException(ResultCodeEnum.STAR_FAIL.getCode(),
                "您不能收藏自已的收藏夹!");
        }
        // 设置必要信息
        PostCategory postCategory = new PostCategory();
        postCategory.setId(targetPostCategory.getId());
        postCategory.setUuid(uuid);
        postCategory.setCategoryAttribute(CategoryAttribute.LINK);
        postCategory.setName(targetPostCategory.getName());

        baseMapper.insert(postCategory);

    }


}
