package io.github.cnsukidayo.wword.core.client;

import io.github.cnsukidayo.wword.model.entity.Divide;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 11:23
 */
@FeignClient("service-core")
public interface DivideFeignClient {
    /**
     * 查询出所有的父划分
     *
     * @return 返回集合不为空nul
     */
    @GetMapping("/remove/core/divide/listParentDivide")
    List<Divide> listParentDivide();
}
