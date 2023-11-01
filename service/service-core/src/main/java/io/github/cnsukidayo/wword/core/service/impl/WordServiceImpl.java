package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.core.dao.WordMapper;
import io.github.cnsukidayo.wword.core.service.WordService;
import io.github.cnsukidayo.wword.model.entity.Word;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/10/31 16:02
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {
    @Override
    public Long countStructure(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");
        return baseMapper.countStructure(wordId);
    }

    @Override
    public Long countValue(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");

        return baseMapper.selectCount(new LambdaQueryWrapper<Word>().eq(Word::getWordId, wordId));
    }

    @Override
    public Word saveWord(Word word) {
        Assert.notNull(word, "word must not be null");
        baseMapper.insert(word);
        return word;
    }
    @Override
    public List<Word> selectWordById(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");
        return baseMapper.selectList(new LambdaQueryWrapper<Word>().eq(Word::getWordId, wordId));
    }

}
