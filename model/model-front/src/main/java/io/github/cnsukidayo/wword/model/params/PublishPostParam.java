package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.InputStream;

/**
 * @author sukidayo
 * @date 2023/9/12 18:17
 */
@Schema(description = "发布帖子参数")
public class PublishPostParam {

    @Schema(description = "文章的标题")
    @NotBlank(message = "文章标题不为空")
    @Size(max = 1024, message = "标题长度不能超过 {max}")
    private String title;

    @Schema(description = "文件上传的输入流")
    @NotNull(message = "没有指定上传的文件")
    private InputStream file;

    public PublishPostParam() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}
