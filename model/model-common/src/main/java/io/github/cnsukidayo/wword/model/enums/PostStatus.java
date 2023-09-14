package io.github.cnsukidayo.wword.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/9/14 11:38
 */
@Schema(description = "帖子状态")
public enum PostStatus {

    TO_CHECK("待审核"),
    PUBLISH_FAIL("发布失败"),
    SUCCESS("发布成功"),
    PRIVATE("私密"),
    FREEZE("冻结");

    private final String value;

    PostStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
