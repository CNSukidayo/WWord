package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.admin.dao.WordStructureMapper;
import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.model.pojo.WordStructure;
import org.springframework.stereotype.Service;

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
    public List<WordStructure> get(Long languageId) {
        return Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<WordStructure>().eq(WordStructure::getLanguageId, languageId))).orElseGet(ArrayList::new);
    }
}
