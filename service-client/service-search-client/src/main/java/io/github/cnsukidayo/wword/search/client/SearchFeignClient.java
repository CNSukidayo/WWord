package io.github.cnsukidayo.wword.search.client;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.param.AddWordESParam;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/11 22:54
 */
@FeignClient(value = "service-search", path = "/remote/es/word")
public interface SearchFeignClient {
    /**
     * 添加一个单词到ES中
     *
     * @param addWordESParam 添加单词的参数
     */
    @PostMapping("save")
    void save(@RequestBody AddWordESParam addWordESParam);

    /**
     * 批量添加单词到ES中
     *
     * @param addWordESParams 集合不为null
     */
    @PostMapping("saveBatch")
    void saveBatch(@RequestBody List<AddWordESParam> addWordESParams);

    /**
     * 分页且模糊搜索单词
     *
     * @param searchWordParam 单词搜索参数不为null
     * @return 返回结果不为null
     */
    @PostMapping("searchWord")
    Page<WordES> searchWord(@Valid @RequestBody SearchWordParam searchWordParam);

    @PostMapping("removeLanguage")
    void removeLanguage(@RequestParam("languageId") Long languageId);

}
