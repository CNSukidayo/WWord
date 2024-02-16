package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.core.dao.WordCategoryMapper;
import io.github.cnsukidayo.wword.core.dao.WordCategoryWordMapper;
import io.github.cnsukidayo.wword.core.service.WordCategoryService;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.dto.WordCategoryWordDTO;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import io.github.cnsukidayo.wword.model.entity.WordCategoryWord;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.vo.WordCategoryDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/27 10:43
 */
@Service
public class WordCategoryServiceImpl extends ServiceImpl<WordCategoryMapper, WordCategory> implements WordCategoryService {

    private final WordCategoryWordMapper wordCategoryWordMapper;

    public WordCategoryServiceImpl(WordCategoryWordMapper wordCategoryWordMapper) {
        this.wordCategoryWordMapper = wordCategoryWordMapper;
    }

    @Override
    public WordCategory save(WordCategory wordCategory, Long uuid) {
        Assert.notNull(wordCategory, "wordCategoryParam must not be null");
        Assert.notNull(uuid, "uuid must not be null");
        wordCategory.setUuid(uuid);
        // 查询出顺序最大的分类
        WordCategory lastWordCategory = baseMapper.findOrderByCategoryOrderFirst(uuid);
        wordCategory.setCategoryOrder(lastWordCategory == null ? 0 : lastWordCategory.getCategoryOrder() + 1);
        baseMapper.insert(wordCategory);
        return wordCategory;
    }

    @Transactional
    @Override
    public void remove(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");
        baseMapper.delete(new LambdaQueryWrapper<WordCategory>()
            .eq(WordCategory::getId, id)
            .eq(WordCategory::getUuid, uuid));
        // 重整收藏夹顺序
        List<WordCategory> wordCategoryList = baseMapper.selectList(new LambdaQueryWrapper<WordCategory>()
            .eq(WordCategory::getUuid, uuid));
        int order = 0;
        for (WordCategory wordCategory : wordCategoryList) {
            wordCategory.setCategoryOrder(order++);
        }
        update(wordCategoryList, uuid);
        // 删除收藏夹关联的单词表
        wordCategoryWordMapper.delete(new LambdaQueryWrapper<WordCategoryWord>().eq(WordCategoryWord::getWordCategoryId, id));
    }

    @Transactional
    @Override
    public void update(List<WordCategory> wordCategoryParams, Long uuid) {
        Assert.notNull(wordCategoryParams, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        // 判断顺序是否有重复的
        if (wordCategoryParams.stream()
            .map(WordCategory::getCategoryOrder)
            .collect(Collectors.toSet())
            .size() != wordCategoryParams.size()) {
            throw new BadRequestException(ResultCodeEnum.ILLEGAL_STATE, "更新失败,收藏夹列表顺序不一致!");
        }

        wordCategoryParams.sort((o1, o2) -> o1.getCategoryOrder() - o2.getCategoryOrder());
        // 必须保证顺序的一致
        int order = 0;
        for (WordCategory wordCategoryParam : wordCategoryParams) {
            wordCategoryParam.setCategoryOrder(order++);
            baseMapper.updateById(wordCategoryParam);
        }

    }

    @Override
    public List<WordCategoryDetailVO> getWordCategoryListAndDetail(Long uuid) {
        Assert.notNull(uuid, "uuid must not be null");

        List<WordCategory> currentUserAllWordCategoryList = baseMapper.selectList(new LambdaQueryWrapper<WordCategory>().eq(WordCategory::getUuid, uuid));
        return currentUserAllWordCategoryList.stream()
            .map(wordCategory -> {
                WordCategoryDetailVO wordCategoryDetailVO = new WordCategoryDetailVO().convertFrom(wordCategory);
                List<WordCategoryWordDTO> wordList = wordCategoryWordMapper.selectList(new LambdaQueryWrapper<WordCategoryWord>()
                        .eq(WordCategoryWord::getWordCategoryId, wordCategoryDetailVO.getId())
                        .orderByAsc(WordCategoryWord::getWordOrder))
                    .stream()
                    .map((Function<WordCategoryWord, WordCategoryWordDTO>) wordCategoryWord -> new WordCategoryWordDTO().convertFrom(wordCategoryWord))
                    .toList();
                wordCategoryDetailVO.setWordCategoryWordList(wordList);
                return wordCategoryDetailVO;
            })
            .sorted((o1, o2) -> o1.getCategoryOrder() - o2.getCategoryOrder())
            .collect(Collectors.toList());
    }

    @Override
    public void addWord(Long wordCategoryId, Long wordId, Long uuid) {
        Assert.notNull(wordCategoryId, "wordId must not be null");
        Assert.notNull(wordId, "wordCategoryId must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<WordCategory>()
                .eq(WordCategory::getId, wordCategoryId)
                .eq(WordCategory::getUuid, uuid)))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS, "目标收藏夹不存在!"));
        // 判断当前收藏夹是否已经有该单词
        Optional.ofNullable(wordCategoryWordMapper.selectOne(new LambdaQueryWrapper<WordCategoryWord>()
                .eq(WordCategoryWord::getWordCategoryId, wordCategoryId)
                .eq(WordCategoryWord::getWordId, wordId)))
            .ifPresent(wordCategoryWord -> {
                throw new BadRequestException(ResultCodeEnum.ALREADY_EXIST, "单词已被收藏!");
            });
        // 查询出当前收藏夹的最后一个单词
        WordCategoryWord lastWord = wordCategoryWordMapper.findOrderByWordOrderDescLast(wordCategoryId);
        WordCategoryWord wordCategoryWord = new WordCategoryWord();
        wordCategoryWord.setWordId(wordId);
        wordCategoryWord.setWordCategoryId(wordCategoryId);
        wordCategoryWord.setWordOrder(lastWord == null ? 0 : lastWord.getWordOrder() + 1);
        wordCategoryWordMapper.insert(wordCategoryWord);
    }

    @Transactional
    @Override
    public void removeWord(Long wordCategoryId, Long wordId, Long uuid) {
        Assert.notNull(wordCategoryId, "wordCategoryWordId must not be null");
        Assert.notNull(wordId, "wordCategoryId must not be null");
        Assert.notNull(uuid, "uuid must not be null");
        Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<WordCategory>()
                .eq(WordCategory::getId, wordCategoryId)
                .eq(WordCategory::getUuid, uuid)))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS, "目标收藏夹不存在!"));
        // 删除单词并重整单词顺序
        wordCategoryWordMapper.delete(new LambdaQueryWrapper<WordCategoryWord>()
            .eq(WordCategoryWord::getWordCategoryId, wordCategoryId)
            .eq(WordCategoryWord::getWordId, wordId));
        List<WordCategoryWord> wordCategoryWordList = Optional.ofNullable(wordCategoryWordMapper.selectList(new LambdaQueryWrapper<WordCategoryWord>()
                .eq(WordCategoryWord::getWordCategoryId, wordCategoryId)
                .orderByAsc(WordCategoryWord::getWordOrder)))
            .orElse(new ArrayList<>());
        int order = 0;
        for (WordCategoryWord wordCategoryWord : wordCategoryWordList) {
            wordCategoryWord.setWordOrder(order++);
            wordCategoryWordMapper.update(wordCategoryWord, new LambdaQueryWrapper<WordCategoryWord>()
                .eq(WordCategoryWord::getWordCategoryId, wordCategoryWord.getWordCategoryId())
                .eq(WordCategoryWord::getWordId, wordCategoryWord.getWordId()));
        }
    }


    @Transactional
    @Override
    public void updateWordOrder(List<WordCategoryWord> wordCategoryWordList, Long uuid) {
        Assert.notNull(wordCategoryWordList, "wordCategoryWordList must not be null");
        Assert.notNull(uuid, "uuid must not be null");
        // 判断顺序是否有重复的
        if (wordCategoryWordList.stream()
            .map(WordCategoryWord::getWordOrder)
            .collect(Collectors.toSet())
            .size() != wordCategoryWordList.size()) {
            throw new BadRequestException(ResultCodeEnum.ILLEGAL_STATE, "更新失败,收藏夹中单词顺序不一致!");
        }
        // 排序后直接更新即可
        wordCategoryWordList.sort((o1, o2) -> o1.getWordOrder() - o2.getWordOrder());
        int order = 0;
        for (WordCategoryWord wordCategoryWord : wordCategoryWordList) {
            wordCategoryWord.setWordOrder(order++);
            wordCategoryWordMapper.update(wordCategoryWord, new LambdaQueryWrapper<WordCategoryWord>()
                .eq(WordCategoryWord::getWordCategoryId, wordCategoryWord.getWordCategoryId())
                .eq(WordCategoryWord::getWordId, wordCategoryWord.getWordId()));
        }
    }
}
