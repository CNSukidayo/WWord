package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.admin.dao.EnWordsMapper;
import io.github.cnsukidayo.wword.admin.dao.WordMapper;
import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.model.pojo.EnWords;
import io.github.cnsukidayo.wword.model.pojo.Word;
import io.github.cnsukidayo.wword.model.pojo.WordStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/31 10:13
 */
@Service
public class WordHandleServiceImpl implements WordHandleService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EnWordsMapper enWordsMapper;

    private final WordStructureService wordStructureService;

    private final WordMapper wordMapper;

    private Long current = 1L;

    public WordHandleServiceImpl(EnWordsMapper enWordsMapper,
                                 WordStructureService wordStructureService,
                                 WordMapper wordMapper) {
        this.enWordsMapper = enWordsMapper;
        this.wordStructureService = wordStructureService;
        this.wordMapper = wordMapper;
    }

    @Override
    public synchronized void handleEnWords() {
        // 得到单词的结构体 Key是属性名,value是结构体对应的id
        Map<String, Long> wordStructureMap = wordStructureService.get(2L)
                .stream()
                .collect(Collectors.toMap(WordStructure::getField, WordStructure::getId));
        while (true) {
            Page<EnWords> enWordsPage = enWordsMapper.selectPage(new Page<>(current, 1000), null);
            List<EnWords> enWordsList = enWordsPage.getRecords();
            for (EnWords enWord : enWordsList) {
                // 单个单词的插入Map
                Map<Long, Word> insertMap = new HashMap<>();
                // 首先先计算出单词的id(单词对应的十进制)
                String wordValue = enWord.getWord();
                Long decimal = (long) wordValue.hashCode();
                // 首先按照分号对单词进行分割;
                String[] translationArray = enWord.getTranslation().split(";");
                log.info("单词: {} 按分号进行分割后的内容为{}", wordValue, translationArray);
                for (String translation : translationArray) {
                    log.info("计算一组中文: {}", translation);
                    // 匹配前缀,以.进行分割索引
                    char[] translationCharArray = translation.toCharArray();
                    int cursor = 0;
                    List<Long> structureIdList = new ArrayList<>();
                    for (int i = 0; i < translationCharArray.length; i++) {
                        if (translationCharArray[i] == '.' && i != 0 && translationCharArray[i - 1] > 96 && translationCharArray[i - 1] < 123) {
                            // 开始往回走,构造key
                            StringBuilder key = new StringBuilder(".");
                            for (int j = i - 1; j >= cursor; j--) {
                                if (translationCharArray[j] < 97 || translationCharArray[j] > 122) {
                                    break;
                                }
                                key.insert(0, translationCharArray[j]);
                            }
                            Long structureId = wordStructureMap.getOrDefault(key.toString(), wordStructureMap.get("expand"));
                            structureIdList.add(structureId);
                            if (insertMap.get(structureId) == null) {
                                Word word = new Word();
                                word.setId(decimal);
                                word.setLanguageId(2L);
                                word.setWordStructureId(structureId);
                                insertMap.put(structureId, word);
                            }
                            cursor = i + 1;
                        }
                    }
                    // 得到当前的翻译
                    String value = new String(translationCharArray, cursor, translationCharArray.length - cursor);
                    // 整个句子作为一个翻译
                    if (CollectionUtils.isEmpty(structureIdList)) {
                        Word word = new Word();
                        word.setId(decimal);
                        word.setLanguageId(2L);
                        Long structureId = wordStructureMap.get("expand");
                        word.setWordStructureId(structureId);
                        insertMap.put(structureId, word);
                        structureIdList.add(structureId);
                    }
                    structureIdList.forEach(structureId -> {
                        Word word = insertMap.get(structureId);
                        if (word.getValue() == null) {
                            word.setValue(value);
                        } else {
                            word.setValue(word.getValue() + "\n" + value);
                        }
                        log.info("中文组: {},分析后得出的单词有:{}----{}", translation, word.getWordStructureId(), word.getValue());
                    });
                }
                // 插入
                wordMapper.save(insertMap.values());
                wordMapper.saveWordId(wordValue, 2L, decimal);
            }
            if (!enWordsPage.hasNext()) {
                break;
            }
            current++;
        }
    }


    /**
     * 26进制转10进制
     *
     * @param str 字符串参数不为null
     * @return 返回字符串代表的10进制
     */
    private Long parseLong(String str) {
        str = str.toLowerCase();
        long decimal = 0L;
        long sum = 1;
        char[] charArray = str.toCharArray();
        for (int i = charArray.length - 1; i > -1; i--) {
            decimal += (charArray[i] - 96) * sum;
            sum *= 26;
        }
        return decimal;
    }
}
