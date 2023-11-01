package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.core.dao.WordStructureMapper;
import io.github.cnsukidayo.wword.core.service.WordStructureService;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author sukidayo
 * @date 2023/7/30 14:29
 */
@Service
public class WordStructureServiceImpl extends ServiceImpl<WordStructureMapper, WordStructure> implements WordStructureService {


    @Override
    public List<WordStructure> selectWordStructureById(Long languageId) {
        Assert.notNull(languageId, "languageId must not be null");

        return Optional.ofNullable(this.list(
            new LambdaQueryWrapper<WordStructure>().eq(WordStructure::getLanguageId, languageId))).orElse(new ArrayList<>());
    }
}
