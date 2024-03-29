package io.github.cnsukidayo.wword.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sukidayo
 * @date 2023/9/12 18:17
 */
@Schema(description = "发布帖子参数")
public class PublishPostParam {

    @Schema(description = "文章的标题")
    @NotBlank(message = "文章标题不为空")
    @Size(max = 255, message = "标题长度不能超过 {max}")
    private String title;

    @Schema(description = "文件上传的输入流")
    @NotNull(message = "上传的文件不为null")
    private MultipartFile markDownFile;

    @Schema(description = "封面文件的输入流")
    @NotNull(message = "必须指定封面")
    private MultipartFile coverFile;

    public PublishPostParam() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getMarkDownFile() {
        return markDownFile;
    }

    public void setMarkDownFile(MultipartFile markDownFile) {
        this.markDownFile = markDownFile;
    }

    public MultipartFile getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(MultipartFile coverFile) {
        this.coverFile = coverFile;
    }
}
