package io.github.cnsukidayo.wword.admin.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.admin.dao.DivideMapper;
import io.github.cnsukidayo.wword.admin.dao.EnWordsMapper;
import io.github.cnsukidayo.wword.admin.dao.WordMapper;
import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.common.exception.FileOperationException;
import io.github.cnsukidayo.wword.common.exception.IllegalStateException;
import io.github.cnsukidayo.wword.common.exception.NonExistsException;
import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.github.cnsukidayo.wword.model.params.UpLoadWordJson;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/7/31 10:13
 */
@Service
public class WordHandleServiceImpl implements WordHandleService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;

    private final EnWordsMapper enWordsMapper;

    private final WordStructureService wordStructureService;

    private final WordMapper wordMapper;

    // todo 远程调用
    private final DivideMapper divideMapper;

    private Long current = 1L;

    private static final String EXPAND = "expand";

    public WordHandleServiceImpl(EnWordsMapper enWordsMapper,
                                 WordStructureService wordStructureService,
                                 WordMapper wordMapper,
                                 DivideMapper divideMapper,
                                 ObjectMapper objectMapper) {
        this.enWordsMapper = enWordsMapper;
        this.wordStructureService = wordStructureService;
        this.wordMapper = wordMapper;
        this.divideMapper = divideMapper;
        this.objectMapper = objectMapper;
    }
    // todo 单词结构重构
//    @Override
//    public synchronized void handleEnWords() {
//        // 得到单词的结构体 Key是属性名,value是结构体对应的id
//        Map<String, Long> wordStructureMap = wordStructureService.get(2L)
//                .stream()
//                .collect(Collectors.toMap(WordStructure::getField, WordStructure::getId));
//        while (true) {
//            Page<EnWords> enWordsPage = enWordsMapper.selectPage(new Page<>(current, 1000), null);
//            List<EnWords> enWordsList = enWordsPage.getRecords();
//            for (EnWords enWord : enWordsList) {
//                // 单个单词的插入Map
//                Map<Long, Word> insertMap = new HashMap<>();
//                // 首先先计算出单词的id(单词对应的十进制)
//                String wordValue = enWord.getWord();
//                Long decimal = (long) wordValue.hashCode();
//                // 首先按照分号对单词进行分割;
//                String[] translationArray = enWord.getTranslation().split(";");
//                log.info("单词: {} 按分号进行分割后的内容为{}", wordValue, translationArray);
//                for (String translation : translationArray) {
//                    log.info("计算一组中文: {}", translation);
//                    // 匹配前缀,以.进行分割索引
//                    char[] translationCharArray = translation.toCharArray();
//                    int cursor = 0;
//                    List<Long> structureIdList = new ArrayList<>();
//                    for (int i = 0; i < translationCharArray.length; i++) {
//                        if (translationCharArray[i] == '.' && i != 0 && translationCharArray[i - 1] > 96 && translationCharArray[i - 1] < 123) {
//                            // 开始往回走,构造key
//                            StringBuilder key = new StringBuilder(".");
//                            for (int j = i - 1; j >= cursor; j--) {
//                                if (translationCharArray[j] < 97 || translationCharArray[j] > 122) {
//                                    break;
//                                }
//                                key.insert(0, translationCharArray[j]);
//                            }
//                            Long structureId = wordStructureMap.getOrDefault(key.toString(), wordStructureMap.get(EXPAND));
//                            structureIdList.add(structureId);
//                            if (insertMap.get(structureId) == null) {
//                                Word word = new Word();
//                                word.setId(decimal);
//                                word.setLanguageId(2L);
//                                word.setWordStructureId(structureId);
//                                insertMap.put(structureId, word);
//                            }
//                            cursor = i + 1;
//                        }
//                    }
//                    // 得到当前的翻译
//                    String value = new String(translationCharArray, cursor, translationCharArray.length - cursor);
//                    // 整个句子作为一个翻译
//                    if (CollectionUtils.isEmpty(structureIdList)) {
//                        Word word = new Word();
//                        word.setId(decimal);
//                        word.setLanguageId(2L);
//                        Long structureId = wordStructureMap.get("expand");
//                        word.setWordStructureId(structureId);
//                        insertMap.put(structureId, word);
//                        structureIdList.add(structureId);
//                    }
//                    structureIdList.forEach(structureId -> {
//                        Word word = insertMap.get(structureId);
//                        if (word.getValue() == null) {
//                            word.setValue(value);
//                        } else {
//                            word.setValue(word.getValue() + "\n" + value);
//                        }
//                        log.info("中文组: {},分析后得出的单词有:{}----{}", translation, word.getWordStructureId(), word.getValue());
//                    });
//                }
//                // 插入
//                wordMapper.replaceWord(insertMap.values());
//                wordMapper.replaceWordId(wordValue, 2L, decimal);
//            }
//            if (!enWordsPage.hasNext()) {
//                break;
//            }
//            current++;
//        }
//    }


    @Override
    public void handleEnWords() {

    }

    @Override
    public void handleJson(UpLoadWordJson upLoadWordJson) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(upLoadWordJson.getFile().getInputStream()), StandardCharsets.UTF_8));
            String signalJson = reader.readLine();
            String value = objectMapper.readValue(signalJson, new TypeReference<String>() {
            });
        } catch (IOException e) {
            throw new FileOperationException("json文件处理异常").setErrorData(upLoadWordJson.getFile().getOriginalFilename());
        }


    }

    @Override
    public void saveWord(AddOrUpdateWordParam addOrUpdateWordParam) {
        Assert.notNull(addOrUpdateWordParam, "addOrUpdateWordParam must not be null");
        // 先获得单词对应的父划分
        Divide divide = Optional.ofNullable(divideMapper.selectById(addOrUpdateWordParam.getDivideId()))
                .orElseThrow(() -> new NonExistsException("指定划分不存在!"));
        // 得到划分所对应的语种id
        Long languageId = divide.getLanguageId();
        // 根据语种id得到单词对应的结构体信息
        List<WordStructure> wordStructures = Optional.ofNullable(wordStructureService.get(languageId)).orElseThrow(() -> new NonExistsException("当前语种还没有定义结构体信息!"));
        // 得到expand的id,如果当前语种没有定义结构信息或者expand属性则会产生异常.
        Long expandId = wordStructures.stream()
                .filter(wordStructure -> wordStructure.getField().equals(EXPAND))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("当前语种的结构体中没有扩展(expand)属性,expand是默认属性,如果不存在会造成数据不一致问题."))
                .getId();
        // 得到当前语种对应结构体属性字段的id
        Set<Long> wordStructureIdSet = wordStructures.stream()
                .map(WordStructure::getId)
                .collect(Collectors.toSet());

        String word = addOrUpdateWordParam.getWord();
        long hashCode = word.hashCode();
        wordMapper.replaceWordId(word, divide.getId(), hashCode);
        List<Word> insertWordList = addOrUpdateWordParam.getWordValueParamList()
                .stream()
                .map(wordValueParam -> {
                    Word result = wordValueParam.convertTo();
                    result.setId(hashCode);
                    result.setDivideId(divide.getId());
                    if (!wordStructureIdSet.contains(result.getWordStructureId())) {
                        result.setWordStructureId(expandId);
                    }
                    return result;
                })
                .toList();
        wordMapper.replaceWord(insertWordList);
    }


}
