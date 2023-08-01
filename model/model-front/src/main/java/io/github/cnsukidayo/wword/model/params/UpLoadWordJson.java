package io.github.cnsukidayo.wword.model.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sukidayo
 * @date 2023/8/1 16:24
 */
@Schema(description = "上传含有word信息的请求报文")
public class UpLoadWordJson {

    @Schema(description = "上传的.json文件")
    @NotNull(message = "上传的文件内容不能为空")
    private MultipartFile file;

    @Schema(description = "指定当前解析出来的单词要添加到那个父划分下")
    @NotNull(message = "必须指定一个划分id")
    private Long divideId;

    public UpLoadWordJson() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getDivideId() {
        return divideId;
    }

    public void setDivideId(Long divideId) {
        this.divideId = divideId;
    }
}
