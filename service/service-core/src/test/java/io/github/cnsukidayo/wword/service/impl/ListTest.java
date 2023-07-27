package io.github.cnsukidayo.wword.service.impl;

import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/27 20:25
 */
public class ListTest {

    @Test
    public void testMap() {
        PostCategory postCategory = new PostCategory();
        postCategory.setUUID(1L);
        postCategory.setName("帖子");

        List<PostCategory> postCategoryList = new ArrayList<>();
        postCategoryList.add(postCategory);
        Map<Long, PostCategory> collect = postCategoryList.stream().collect(Collectors.toMap(PostCategory::getUUID, postCategory1 -> postCategory1));


    }

}
