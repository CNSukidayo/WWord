package io.github.cnsukidayo.wword.model.params;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/31 20:58
 */
@Schema(description = "添加或更新一个单词的请求体")
public class AddOrUpdateWordParam {

    @Schema(description = "划分id")
    @NotNull(message = "划分id不为null")
    private Long divideId;

    @Schema(description = "单词内容")
    @NotEmpty(message = "单词内容不能为null")
    private String word;

    @NotEmpty(message = "单词更新或添加的内容不能为null")
    @Valid
    List<WordValueParam> wordValueParamList;

    @Schema(description = "单词包含信息设置")
    public static class WordValueParam implements InputConverter<Word> {

        @Schema(description = "单词结构体对应属性id")
        @NotNull(message = "单词指定的属性字段不能为空")
        private Long wordStructureId;

        @Schema(description = "单词属性字段对应的value")
        @NotEmpty(message = "单词属性字段value值不能为空")
        private String value;

        @Schema(description = "是否删除该属性值,1代表删除")
        @Max(value = 1, message = "属性值必须为1代表删除,0代表添加或修改.")
        @Min(value = 0, message = "属性值必须为1代表删除,0代表添加或修改.")
        private Integer isDeleted = 0;


        public WordValueParam() {
        }

        public Long getWordStructureId() {
            return wordStructureId;
        }

        public void setWordStructureId(Long wordStructureId) {
            this.wordStructureId = wordStructureId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Integer getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }
    }

    public Long getDivideId() {
        return divideId;
    }

    public void setDivideId(Long divideId) {
        this.divideId = divideId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<WordValueParam> getWordValueParamList() {
        return wordValueParamList;
    }

    public void setWordValueParamList(List<WordValueParam> wordValueParamList) {
        this.wordValueParamList = wordValueParamList;
    }
}
