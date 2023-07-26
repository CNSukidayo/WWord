package io.github.cnsukidayo.wword.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author sukidayo
 * @date 2023/7/23 15:25
 */
@Schema(description = "学校信息")
public class UniversityDTO  {

    @Schema(description = "学校名称")
    private String schoolName;

    @Schema(description = "学校代码")
    private Integer code;

    @Schema(description = "学校所在的城市.地级市或直辖市;例如南京市、苏州市、上海市")
    private String city;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
