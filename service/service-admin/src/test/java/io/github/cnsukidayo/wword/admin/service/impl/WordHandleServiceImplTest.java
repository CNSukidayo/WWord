package io.github.cnsukidayo.wword.admin.service.impl;

import io.github.cnsukidayo.wword.model.entity.Word;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author sukidayo
 * @date 2023/7/31 10:27
 */
public class WordHandleServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testWeight() {
    }

    @Test
    public void testHandle() {
        handle("adj.&n.阿布维利文化(的)(指欧洲旧石器时代初期较早阶段的文化);adj.很好的");
        handle("adv.向船尾;prep.在...后");
    }

    private void handle(String wordValue) {

        Map<String, Long> wordStructureMap = new HashMap<>() {{
            put("adj.", 1L);
            put("adv.", 2L);
            put("n.", 3L);
            put("expand.", 4L);
        }};

        // 单个单词的插入Map
        Map<Long, Word> insertMap = new HashMap<>();
        // 首先先计算出单词的id(单词对应的十进制)
        Long decimal = parseLong(wordValue);
        // 首先按照分号对单词进行分割;
        String[] translationArray = wordValue.split(";");
        log.info("\n单词: {} 按分号进行分割后的内容为{}", wordValue, translationArray);
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
                    Long structureId = wordStructureMap.getOrDefault(key.toString(), wordStructureMap.get("expand."));
                    structureIdList.add(structureId);
                    if (insertMap.get(structureId) == null) {
                        Word word = new Word();
                        word.setId(decimal);
                        word.setWordStructureId(structureId);
                        insertMap.put(structureId, word);
                    }
                    cursor = i + 1;
                }
            }
            // 得到当前的翻译
            String value = new String(translationCharArray, cursor, translationCharArray.length - cursor);
            log.info("中文组: {},分析后得出的单词有:", translation);
            structureIdList.forEach(structureId -> {
                Word word = insertMap.get(structureId);
                if (word.getValue() == null) {
                    word.setValue(value);
                } else {
                    word.setValue(word.getValue() + "\n" + value);
                }
                log.info("{}----{}", word.getWordStructureId(), word.getValue());
            });
        }
    }

    private Long parseLong(String str) {
        char[] charArray = str.toCharArray();
        str = str.toLowerCase();
        long weight = 0L;
        long sum = 1;
        for (int i = charArray.length - 1; i > -1; i--) {
            weight += (charArray[i] - 96) * sum;
            sum *= 26;
        }
        return weight;
    }
}