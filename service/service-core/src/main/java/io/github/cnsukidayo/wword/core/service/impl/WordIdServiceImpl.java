package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.core.dao.WordIdMapper;
import io.github.cnsukidayo.wword.core.dao.WordMapper;
import io.github.cnsukidayo.wword.core.service.WordIdService;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 14:24
 */
@Service
public class WordIdServiceImpl extends ServiceImpl<WordIdMapper, WordId> implements WordIdService {

    private final WordIdMapper wordIdMapper;

    private final WordMapper wordMapper;

    public WordIdServiceImpl(WordIdMapper wordIdMapper,
                             WordMapper wordMapper) {
        this.wordIdMapper = wordIdMapper;
        this.wordMapper = wordMapper;
    }

    @Override
    public List<WordId> selectWordIdByDivideId(Long divideId) {
        Assert.notNull(divideId, "divideId must not be null");
        return wordIdMapper.selectList(new LambdaQueryWrapper<WordId>().eq(WordId::getDivideId, divideId));
    }

    @Override
    public List<WordId> selectSameWordIdWord(WordId wordId) {
        Assert.notNull(wordId, "wordId must not be null");
        Assert.notNull(wordId.getId(), "word.id must not be null");
        Assert.notNull(wordId.getWord(), "word.word must not be null");

        return wordIdMapper.selectList(new LambdaQueryWrapper<WordId>().gt(WordId::getDivideId, wordId.getDivideId())
            .eq(WordId::getWord, wordId.getWord()));
    }

    @Override
    public List<Word> selectWordById(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");
        return wordMapper.selectList(new LambdaQueryWrapper<Word>().eq(Word::getWordId, wordId));
    }

    @Override
    public Word saveWord(Word word) {
        Assert.notNull(word, "word must not be null");
        wordMapper.insert(word);
        return word;
    }

    @Override
    public Long countStructure(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");
        return wordMapper.countStructure(wordId);
    }

    @Override
    public Long countValue(Long wordId) {
        Assert.notNull(wordId, "wordId must not be null");

        return wordMapper.selectCount(new LambdaQueryWrapper<Word>().eq(Word::getWordId, wordId));
    }

    @Override
    public Boolean exist(String word, Long divideId) {
        Assert.hasText(word, "word must not be empty");
        Assert.notNull(divideId, "divideId must not be null");
        return wordIdMapper.exists(new LambdaQueryWrapper<WordId>()
            .eq(WordId::getDivideId, divideId)
            .eq(WordId::getWord, word));
    }


}
