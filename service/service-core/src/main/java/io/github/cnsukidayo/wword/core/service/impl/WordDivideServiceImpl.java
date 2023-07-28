package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.core.dao.WordDivideMapper;
import io.github.cnsukidayo.wword.core.service.WordDivideService;
import io.github.cnsukidayo.wword.core.dao.LanguageClassMapper;
import io.github.cnsukidayo.wword.model.dto.WordDivideDTO;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;
import io.github.cnsukidayo.wword.model.pojo.WordDivide;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 16:16
 */
@Service
public class WordDivideServiceImpl extends ServiceImpl<WordDivideMapper, WordDivide> implements WordDivideService {

    private final LanguageClassMapper languageClassMapper;

    public WordDivideServiceImpl(LanguageClassMapper languageClassMapper) {
        this.languageClassMapper = languageClassMapper;
    }

    @Override
    public List<LanguageClass> listLanguage() {
        return languageClassMapper.selectList(null);
    }

    @Override
    public List<WordDivideDTO> listOfficialDivide(Long languageId, Long uuid) {
        // 根据languageId和uuid查询到所有的划分

        return null;
    }
}
