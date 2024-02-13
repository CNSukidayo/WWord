package io.github.cnsukidayo.wword.search.service;

import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.param.AddWordESParam;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/11 8:53
 */
public interface WordESService {
    /**
     * 添加一个单词到ES中
     *
     * @param addWordESParam 添加单词的参数
     */
    void save(AddWordESParam addWordESParam);

    /**
     * 批量添加单词到ES中
     *
     * @param addWordESParams 集合不为null
     */
    void saveBatch(List<AddWordESParam> addWordESParams);

    /**
     * 分页且模糊搜索单词
     *
     * @param searchWordParam 单词搜索参数不为null
     * @return 返回结果不为null
     */
    Page<WordES> searchWord(SearchWordParam searchWordParam);

    /**
     * 删除某个语种的所有单词
     *
     * @param languageId 语种的ID
     */
    void removeLanguage(Long languageId);
}
