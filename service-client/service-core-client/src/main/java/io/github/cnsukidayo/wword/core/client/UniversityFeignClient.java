package io.github.cnsukidayo.wword.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sukidayo
 * @date 2023/9/8 15:41
 */
@FeignClient("service-core")
public interface UniversityFeignClient {

    /**
     * 判断是否存在一个学校
     *
     * @param schoolName 学校名称参数不为空
     * @return 返回是否存在
     */
    @GetMapping("/remote/u/university/has_university")
    Boolean hasUniversity(@RequestParam("school_name") String schoolName);

}
