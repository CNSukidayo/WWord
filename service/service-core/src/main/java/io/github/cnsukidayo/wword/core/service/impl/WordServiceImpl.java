package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.cnsukidayo.wword.common.utils.RedisUtils;
import io.github.cnsukidayo.wword.core.dao.WordMapper;
import io.github.cnsukidayo.wword.core.service.WordService;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.global.support.constant.RedisConstant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/10/31 16:02
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    private final StringRedisTemplate redisTemplate;

    public WordServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

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
        String wordDetail = redisTemplate.opsForValue().get(String.format(RedisConstant.WORD_DETAIL, wordId));
        if (wordDetail != null) {
            return JsonUtils.jsonToObject(wordDetail, new TypeReference<>() {
            });
        }
        // 从数据库中查询并添加缓存到数据库中
        List<Word> wordList = baseMapper.selectList(new LambdaQueryWrapper<Word>().eq(Word::getWordId, wordId));
        redisTemplate.opsForValue().set(String.format(RedisConstant.WORD_DETAIL, wordId),
            JsonUtils.objectToJson(wordList),
            RedisUtils.randomSecond(RedisUtils.ONE_DAY),
            RedisUtils.COMMON_TIME_UTIL);
        return wordList;
    }

    @Override
    public List<Word> batchSelectWordById(List<Long> wordIds) {
        Assert.notNull(wordIds, "wordIds must not be null");

        List<Word> result = new ArrayList<>();
        List<Long> wordIdMissList = new ArrayList<>(wordIds.size() / 3);
        for (Long wordId : wordIds) {
            String wordDetail = redisTemplate.opsForValue().get(String.format(RedisConstant.WORD_DETAIL, wordId));
            if (wordDetail != null) {
                result.addAll(JsonUtils.jsonToObject(wordDetail, new TypeReference<List<Word>>() {
                }));
            } else {
                wordIdMissList.add(wordId);
            }
        }
        if (!CollectionUtils.isEmpty(wordIdMissList)) {
            // 拿到所有单词的元数据后;按照单词id进行归类;将本次查询的所有单词添加进缓存
            List<Word> wordMissList = baseMapper.selectList(new LambdaQueryWrapper<Word>().in(Word::getWordId, wordIdMissList));
            Map<Long, List<Word>> wordMissMap = wordMissList.stream().collect(Collectors.groupingBy(Word::getWordId));
            for (Map.Entry<Long, List<Word>> wordEntry : wordMissMap.entrySet()) {
                redisTemplate.opsForValue().set(String.format(RedisConstant.WORD_DETAIL, wordEntry.getKey()),
                    JsonUtils.objectToJson(wordEntry.getValue()),
                    RedisUtils.randomSecond(RedisUtils.TOW_HOUR),
                    RedisUtils.COMMON_TIME_UTIL);
            }
            result.addAll(wordMissList);
        }
        return result;
    }

}
