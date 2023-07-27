package io.github.cnsukidayo.wword.service.impl;

import io.github.cnsukidayo.wword.model.dto.PostCategoryDTO;
import io.github.cnsukidayo.wword.model.pojo.PostCategory;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/7/27 11:36
 */
public class ConvertTest {

    @Test
    public void convertToDTO() {
        PostCategory postCategory = new PostCategory();
        postCategory.setUUID(10L);
        postCategory.setName("默认");
        PostCategoryDTO postCategoryDTO = postCategory.convertToDTO(new PostCategoryDTO());
        System.out.println(postCategoryDTO);
    }

    @Test
    public void convert() {
    }

}
