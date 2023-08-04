package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.admin.dao.DivideMapper;
import io.github.cnsukidayo.wword.admin.dao.EnWordsMapper;
import io.github.cnsukidayo.wword.admin.dao.WordIdMapper;
import io.github.cnsukidayo.wword.admin.dao.WordMapper;
import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.common.exception.FileOperationException;
import io.github.cnsukidayo.wword.common.exception.IllegalStateException;
import io.github.cnsukidayo.wword.common.exception.NonExistsException;
import io.github.cnsukidayo.wword.model.bo.JsonWordBO;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordId;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.github.cnsukidayo.wword.model.params.UpLoadWordJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    private final WordIdMapper wordIdMapper;

    // todo 远程调用
    private final DivideMapper divideMapper;

    private Long current = 1L;

    private static final String EXPAND = "expand";

    public WordHandleServiceImpl(EnWordsMapper enWordsMapper,
                                 WordStructureService wordStructureService,
                                 WordMapper wordMapper,
                                 DivideMapper divideMapper,
                                 WordIdMapper wordIdMapper,
                                 ObjectMapper objectMapper) {
        this.enWordsMapper = enWordsMapper;
        this.wordStructureService = wordStructureService;
        this.wordMapper = wordMapper;
        this.divideMapper = divideMapper;
        this.wordIdMapper = wordIdMapper;
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

    @Async
    @Transactional
    @Override
    public void handleJson(UpLoadWordJson upLoadWordJson, InputStream jsonInputStream) {
        Assert.notNull(upLoadWordJson, "upLoadWordJson must not be null");
        Assert.notNull(jsonInputStream, "jsonInputStream must not be null");

        // 先获得单词对应的父划分,划分必须是父划分不能是子划分
        Divide divide = Optional.ofNullable(divideMapper.selectById(upLoadWordJson.getDivideId()))
                .filter(test -> test.getParentId() == -1)
                .orElseThrow(() -> new NonExistsException("指定划分不存在!"));
        // 得到划分对应的id
        Long divideId = divide.getId();
        // 得到划分所对应的语种id
        Long languageId = divide.getLanguageId();
        // 根据语种id得到单词对应的结构体信息,并将其封装为K-V形式.Key是属性名称,value是属性名称对应的id
        Map<String, Long> wordStructureMap = Optional.ofNullable(wordStructureService.get(languageId))
                .orElseThrow(() -> new NonExistsException("当前语种还没有定义结构体信息!"))
                .stream()
                .collect(Collectors.toMap(WordStructure::getField, WordStructure::getId));
        // 得到当前expand字段对应的id
        hasFields(wordStructureMap, EXPAND, "wordOrigin");
        Long expandId = wordStructureMap.get(EXPAND);
        long startTime = System.currentTimeMillis();
        log.info("start compute divide [{}] ;admin upload file is [{}]", divide.getName(), upLoadWordJson.getFile().getOriginalFilename());

        StringBuilder copy = new StringBuilder();
        // 读取单词,必须使用拷贝流
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(jsonInputStream), StandardCharsets.UTF_8))) {
            // 循环读取
            String signalJson;
            while (null != (signalJson = reader.readLine())) {
                copy.append(signalJson).append('\n');
                // 转换单词对象
                JsonWordBO jsonWordBO = objectMapper.readValue(signalJson, JsonWordBO.class);
                // 首先插入单词到word_id表中
                String wordOrigin = jsonWordBO.getHeadWord();
                WordId wordId = new WordId();
                wordId.setWord(wordOrigin);
                wordId.setDivideId(divideId);
                wordIdMapper.insert(wordId);
                Long wordIdValue = wordId.getId();
                Word tempWord = null;
                // 得到单词的原内容
                tempWord = createWord(wordIdValue);
                tempWord.setWordStructureId(wordStructureMap.get("wordOrigin"));
                tempWord.setValue(wordOrigin);
                wordMapper.insert(tempWord);
                // 得到美式音标
                tempWord = createWord(wordIdValue);
                String usphone = jsonWordBO.getContent().getWord().getContent().getUsphone();
                tempWord.setWordStructureId(wordStructureMap.getOrDefault("usPhonetic", expandId));
                tempWord.setValue(usphone);
                wordMapper.insert(tempWord);
                // 得到英式音标
                tempWord = createWord(wordIdValue);
                String ukphone = jsonWordBO.getContent().getWord().getContent().getUkphone();
                tempWord.setWordStructureId(wordStructureMap.getOrDefault("ukPhonetic", expandId));
                tempWord.setValue(ukphone);
                wordMapper.insert(tempWord);
                // 封装单词相关测试
                try {
                    for (JsonWordBO.Content.Word.InternalContent.Exam exam :
                            Optional.ofNullable(jsonWordBO.getContent()
                                    .getWord()
                                    .getContent()
                                    .getExam()).orElseGet(() -> new JsonWordBO.Content.Word.InternalContent.Exam[0])) {
                        Long groupId;
                        // 得到测试的问题
                        tempWord = createWord(wordIdValue);
                        String question = exam.getQuestion();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("question", expandId));
                        tempWord.setValue(question);
                        wordMapper.insert(tempWord);
                        groupId = tempWord.getId();
                        // 得到测试的答案的解释
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String explain = exam.getAnswer().getExplain();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("questionExplain", expandId));
                        tempWord.setValue(explain);
                        wordMapper.insert(tempWord);
                        // 得到测试的正确答案的索引号
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        Integer rightIndex = exam.getAnswer().getRightIndex();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("rightIndex", expandId));
                        tempWord.setValue(String.valueOf(rightIndex));
                        wordMapper.insert(tempWord);
                        try {
                            // 得到所有的选项
                            for (JsonWordBO.Content.Word.InternalContent.Exam.Choice choice :
                                    Optional.ofNullable(exam.getChoices()).orElseGet(() -> new JsonWordBO.Content.Word.InternalContent.Exam.Choice[0])) {
                                // 得到选项对应的索引,选项索引和测试问题之间是绑定的
                                tempWord = createWord(wordIdValue);
                                tempWord.setGroupId(groupId);
                                String choiceIndex = choice.getChoiceIndex();
                                tempWord.setWordStructureId(wordStructureMap.getOrDefault("choiceIndex", expandId));
                                tempWord.setValue(choiceIndex);
                                wordMapper.insert(tempWord);
                                Long choiceIndexId = tempWord.getId();
                                // 得到选项对应的值,选项内容和选项索引是绑定的
                                tempWord = createWord(wordIdValue);
                                tempWord.setGroupId(choiceIndexId);
                                String choiceValue = choice.getChoice();
                                tempWord.setWordStructureId(wordStructureMap.getOrDefault("choiceValue", expandId));
                                tempWord.setValue(choiceValue);
                                wordMapper.insert(tempWord);
                            }
                        } catch (NullPointerException e) {
                            log.debug("单词 [{}] 没有选项信息", wordOrigin);
                        }
                    }
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有相关例题信息", wordOrigin);
                }
                try {
                    // 封装例句相关内容
                    for (JsonWordBO.Content.Word.InternalContent.Sentence.InternalSentence internalSentence :
                            Optional.ofNullable(jsonWordBO.getContent()
                                    .getWord()
                                    .getContent()
                                    .getSentence()
                                    .getSentences()).orElseGet(() -> new JsonWordBO.Content.Word.InternalContent.Sentence.InternalSentence[0])) {
                        // 得到例句的英文
                        tempWord = createWord(wordIdValue);
                        String sentenceValue = internalSentence.getsContent();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sentence", expandId));
                        tempWord.setValue(sentenceValue);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到例句英文的翻译,例句的英文翻译需要和例句英文进行绑定
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String sentenceTranslation = internalSentence.getsCn();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sentenceTranslation", expandId));
                        tempWord.setValue(sentenceTranslation);
                        wordMapper.insert(tempWord);
                    }
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有例句信息", wordOrigin);
                }
                // todo 封装近义词相关,等单词先录入一遍之后再处理近义词相关内容
                try {
                    // 封装短语相关内容
                    for (JsonWordBO.Content.Word.InternalContent.Phrase.InternalPhrase phrase :
                            Optional.ofNullable(jsonWordBO.getContent()
                                    .getWord()
                                    .getContent()
                                    .getPhrase()
                                    .getPhrases()).orElseGet(() -> new JsonWordBO.Content.Word.InternalContent.Phrase.InternalPhrase[0])) {
                        // 得到短语的英文
                        tempWord = createWord(wordIdValue);
                        String phraseValue = phrase.getpContent();
                        // 得到短语的中文翻译
                        String phraseTranslation = phrase.getpCn();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("phrase", expandId));
                        tempWord.setValue(phraseValue);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到短语对应的中文
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("phraseTranslation", expandId));
                        tempWord.setValue(phraseTranslation);
                        wordMapper.insert(tempWord);
                    }
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有短语信息", wordOrigin);
                }
                // todo 封装同根词,等单词先录入一遍之后再处理同根词
                try {
                    // 封装单词的中文翻译
                    for (JsonWordBO.Content.Word.InternalContent.Tran tran :
                            jsonWordBO.getContent().getWord().getContent().getTrans()) {
                        tempWord = createWord(wordIdValue);
                        String pos;
                        if (!(pos = tran.getPos().trim()).endsWith(".")) {
                            pos += ".";
                        }
                        // 得到当前词性的中文翻译
                        String translationValue = tran.getTranCn();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault(pos, expandId));
                        tempWord.setValue(translationValue);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到单词的英文解释;英文的格式就直接采用 词性.英文描述
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String describeEnglish = tran.getTranOther();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("describeEnglish", expandId));
                        tempWord.setValue(describeEnglish);
                        wordMapper.insert(tempWord);
                    }
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有中文翻译信息", wordOrigin);
                }
                try {
                    // 得到单词的图片信息
                    tempWord = createWord(wordIdValue);
                    String picture = jsonWordBO.getContent().getWord().getContent().getPicture();
                    tempWord.setWordStructureId(wordStructureMap.getOrDefault("picture", expandId));
                    tempWord.setValue(picture);
                    wordMapper.insert(tempWord);
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有图片信息", wordOrigin);
                }
                try {
                    // 封装单词的真题例句信息
                    for (JsonWordBO.Content.Word.InternalContent.RealExamSentence.InternalRealSentence realSentence :
                            jsonWordBO.getContent().getWord().getContent().getRealExamSentence().getSentences()) {
                        // 得到例句英文
                        tempWord = createWord(wordIdValue);
                        String realSentenceValue = realSentence.getsContent();
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("realExamSentence", expandId));
                        tempWord.setValue(realSentenceValue);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到例句的paper信息(是哪一套试卷)
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String paper = Optional.ofNullable(realSentence.getSourceInfo().getPaper()).orElse("");
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sourcePaper", expandId));
                        tempWord.setValue(paper);
                        wordMapper.insert(tempWord);
                        // 得到例句的level信息(是哪个等级)
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String level = Optional.ofNullable(realSentence.getSourceInfo().getLevel()).orElse("");
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sourceLevel", expandId));
                        tempWord.setValue(level);
                        wordMapper.insert(tempWord);
                        // 得到例句年份信息
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String year = Optional.ofNullable(realSentence.getSourceInfo().getYear()).orElse("");
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sourceYear", expandId));
                        tempWord.setValue(year);
                        wordMapper.insert(tempWord);
                        // 得到例句的类型信息
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        String type = Optional.ofNullable(realSentence.getSourceInfo().getType()).orElse("");
                        tempWord.setWordStructureId(wordStructureMap.getOrDefault("sourceType", expandId));
                        tempWord.setValue(type);
                        wordMapper.insert(tempWord);
                    }
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有真题例句信息", wordOrigin);
                }
                try {
                    // 封装单词的记忆方法信息
                    tempWord = createWord(wordIdValue);
                    String remMethod = jsonWordBO.getContent().getWord().getContent().getRemMethod().getVal();
                    tempWord.setWordStructureId(wordStructureMap.getOrDefault("rememberMethod", expandId));
                    tempWord.setValue(remMethod);
                    wordMapper.insert(tempWord);
                } catch (NullPointerException e) {
                    log.debug("单词 [{}] 没有记忆方法信息", wordOrigin);
                }
            }
        } catch (Exception e) {
            log.error("json handle fail", e);
            throw new FileOperationException("json文件处理异常")
                    .setErrorData(upLoadWordJson.getFile().getOriginalFilename() + "划分名称:" + divide.getName());
        }

        // 读取单词,必须使用拷贝流
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(copy.toString().getBytes()), StandardCharsets.UTF_8))) {
            // 循环读取
            String signalJson;
            while (null != (signalJson = reader.readLine())) {
                if (!StringUtils.hasText(signalJson)) return;

                JsonWordBO jsonWordBO = objectMapper.readValue(signalJson, JsonWordBO.class);
                String wordOrigin = jsonWordBO.getHeadWord();
                // 首先要得到当前单词在word_id表中的id
                LambdaQueryWrapper<WordId> wordIdQuery = new LambdaQueryWrapper<>();
                wordIdQuery.eq(WordId::getDivideId, divide.getId())
                        .eq(WordId::getWord, wordOrigin);
                Long wordIdValue = Optional.ofNullable(wordIdMapper.selectOne(wordIdQuery)).orElseThrow(() -> new IllegalStateException("can not find word" + wordOrigin)).getId();
                Word tempWord = null;
                // 得到同根词
                try {
                    for (JsonWordBO.Content.Word.InternalContent.Syno.InternalSyno syno : jsonWordBO.getContent().getWord().getContent().getSyno().getSynos()) {
                        // 得到单词的联想词的词性
                        String synoPos = syno.getPos();
                        tempWord = createWord(wordIdValue);
                        tempWord.setWordStructureId(wordStructureMap.get("synoPos"));
                        tempWord.setValue(synoPos);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到联想词的翻译
                        String synoTran = syno.getTran();
                        tempWord = createWord(wordIdValue);
                        tempWord.setGroupId(groupId);
                        tempWord.setWordStructureId(wordStructureMap.get("synoTranslation"));
                        tempWord.setValue(synoTran);
                        wordMapper.insert(tempWord);
                        // 得到当前词性对应的联想词
                        for (JsonWordBO.Content.Word.InternalContent.Syno.InternalSyno.HWD hwd : syno.getHwds()) {
                            String w = hwd.getW();
                            tempWord = createWord(wordIdValue);
                            tempWord.setGroupId(groupId);
                            tempWord.setWordStructureId(wordStructureMap.get("synoWord"));
                            tempWord.setValue(w);
                            wordMapper.insert(tempWord);
                            Long internalGroupId = tempWord.getId();
                            // 根据联想词从当前单词本中找出对应单词的id
                            LambdaQueryWrapper<WordId> originIdLambdaQueryWrapper = new LambdaQueryWrapper<>();
                            originIdLambdaQueryWrapper.eq(WordId::getDivideId, divideId)
                                    .eq(WordId::getWord, w);
                            WordId wordId = wordIdMapper.selectOne(originIdLambdaQueryWrapper);
                            if (wordId != null) {
                                // 封装id信息
                                tempWord = createWord(wordIdValue);
                                tempWord.setGroupId(internalGroupId);
                                tempWord.setWordStructureId(wordStructureMap.get("synoWordId"));
                                tempWord.setValue(String.valueOf(wordId.getId()));
                                wordMapper.insert(tempWord);
                            }
                        }
                    }

                } catch (NullPointerException e) {
                    log.debug("word [{}] don't have syno field", wordOrigin);
                }
                try {
                    // 封装单词的同根词
                    for (JsonWordBO.Content.Word.InternalContent.RelWord.InternalRelWord relWord : jsonWordBO.getContent().getWord().getContent().getRelWord().getRels()) {
                        // 得到同根词的词性
                        String relPos = relWord.getPos();
                        tempWord = createWord(wordIdValue);
                        tempWord.setWordStructureId(wordStructureMap.get("relPos"));
                        tempWord.setValue(relPos);
                        wordMapper.insert(tempWord);
                        Long groupId = tempWord.getId();
                        // 得到同根词的内容
                        for (JsonWordBO.Content.Word.InternalContent.RelWord.InternalRelWord.InternalWord relWordInfo : relWord.getWords()) {
                            String hwd = relWordInfo.getHwd();
                            tempWord = createWord(wordIdValue);
                            tempWord.setGroupId(groupId);
                            tempWord.setWordStructureId(wordStructureMap.get("relWord"));
                            tempWord.setValue(hwd);
                            wordMapper.insert(tempWord);
                            Long internalGroupId = tempWord.getId();
                            // 得到对应的翻译
                            String tran = relWordInfo.getTran();
                            tempWord = createWord(wordIdValue);
                            tempWord.setGroupId(internalGroupId);
                            tempWord.setWordStructureId(wordStructureMap.get("relWordTranslation"));
                            tempWord.setValue(tran);
                            wordMapper.insert(tempWord);
                            // 根据同根词从当前单词本中找出对应单词的id
                            LambdaQueryWrapper<WordId> originIdLambdaQueryWrapper = new LambdaQueryWrapper<>();
                            originIdLambdaQueryWrapper.eq(WordId::getDivideId, divideId)
                                    .eq(WordId::getWord, hwd);
                            WordId wordId = wordIdMapper.selectOne(originIdLambdaQueryWrapper);
                            if (wordId != null) {
                                // 封装id信息
                                tempWord = createWord(wordIdValue);
                                tempWord.setGroupId(internalGroupId);
                                tempWord.setWordStructureId(wordStructureMap.get("relWordId"));
                                tempWord.setValue(String.valueOf(wordId.getId()));
                                wordMapper.insert(tempWord);
                            }

                        }
                    }
                } catch (NullPointerException e) {
                    log.debug("word [{}] don't have rel field", wordOrigin);
                }
            }


        } catch (Exception e) {
            log.error("json handle fail", e);
            throw new FileOperationException("json文件处理异常")
                    .setErrorData(upLoadWordJson.getFile().getOriginalFilename() + "划分名称:" + divide.getName());
        }

        log.info("compute divide [{}] complete; consume time:[{}] ms", divide.getName(), System.currentTimeMillis() - startTime);
    }

    @Override
    public void saveWord(AddOrUpdateWordParam addOrUpdateWordParam) {
//        Assert.notNull(addOrUpdateWordParam, "addOrUpdateWordParam must not be null");
//        // 先获得单词对应的父划分
//        Divide divide = Optional.ofNullable(divideMapper.selectById(addOrUpdateWordParam.getDivideId()))
//                .orElseThrow(() -> new NonExistsException("指定划分不存在!"));
//        // 得到划分所对应的语种id
//        Long languageId = divide.getLanguageId();
//        // 根据语种id得到单词对应的结构体信息
//        List<WordStructure> wordStructures = Optional.ofNullable(wordStructureService.get(languageId)).orElseThrow(() -> new NonExistsException("当前语种还没有定义结构体信息!"));
//        // 得到expand的id,如果当前语种没有定义结构信息或者expand属性则会产生异常.
//        Long expandId = wordStructures.stream()
//                .filter(wordStructure -> wordStructure.getField().equals(EXPAND))
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("当前语种的结构体中没有扩展(expand)属性,expand是默认属性,如果不存在会造成数据不一致问题."))
//                .getId();
//        // 得到当前语种对应结构体属性字段的id
//        Set<Long> wordStructureIdSet = wordStructures.stream()
//                .map(WordStructure::getId)
//                .collect(Collectors.toSet());
//
//        String word = addOrUpdateWordParam.getWord();
//        long hashCode = word.hashCode();
//        wordMapper.replaceWordId(word, divide.getId(), hashCode);
//        List<Word> insertWordList = addOrUpdateWordParam.getWordValueParamList()
//                .stream()
//                .map(wordValueParam -> {
//                    Word result = wordValueParam.convertTo();
//                    result.setId(hashCode);
//                    result.setDivideId(divide.getId());
//                    if (!wordStructureIdSet.contains(result.getWordStructureId())) {
//                        result.setWordStructureId(expandId);
//                    }
//                    return result;
//                })
//                .toList();
//        wordMapper.replaceWord(insertWordList);
    }

    /**
     * 根据单词id快捷创建一个单词,并将其添加到待插入列表
     *
     * @param id 单词id不为null
     * @return 返回word实例
     */
    private Word createWord(Long id) {
        Assert.notNull(id, "id must not be null");
        Word word = new Word();
        word.setWordId(id);
        return word;
    }

    /**
     * 检查wordStructureMap当前结构体中是否全部拥有这些字段
     *
     * @param wordStructureMap 结构体map不能为null
     * @param fields           字段不能为null
     */
    private void hasFields(Map<String, Long> wordStructureMap, String... fields) {
        Assert.notNull(wordStructureMap, "wordStructureMap must not be null");
        Assert.notNull(fields, "fields must not be null");
        List<String> notExistList = new ArrayList<>();
        for (String field : fields) {
            if (!wordStructureMap.containsKey(field)) {
                notExistList.add(field);
            }
        }
        if (!CollectionUtils.isEmpty(notExistList)) {
            log.error("字段检查错误,当前语种的结构体中缺少{}字段", notExistList);
            throw new IllegalStateException("当前语种的结构体中缺少必要字段" + Arrays.toString(notExistList.toArray()) + "如果不存在会造成数据不一致问题.");
        }
    }


}
