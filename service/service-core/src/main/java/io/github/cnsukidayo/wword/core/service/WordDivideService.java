package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.dto.WordDivideDTO;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;
import io.github.cnsukidayo.wword.model.pojo.WordDivide;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 15:23
 */
public interface WordDivideService extends IService<WordDivide> {

    /**
     * 查询所有语言分类
     *
     * @return 返回语言
     */
    List<LanguageClass> listLanguage();

    /**
     * 查询某个语种的所有官方划分
     *
     * @param languageId 语种id不为null
     * @param uuid       用户id不为null
     * @return 返回所有官方划分的集合
     */
    List<WordDivideDTO> listOfficialDivide(Long languageId, Long uuid);
}
