package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.admin.dao.WordIdMapper;
import io.github.cnsukidayo.wword.admin.dao.WordMapper;
import io.github.cnsukidayo.wword.admin.service.WordHandleService;
import io.github.cnsukidayo.wword.admin.service.WordStructureService;
import io.github.cnsukidayo.wword.core.client.CoreFeignClient;
import io.github.cnsukidayo.wword.global.exception.BadRequestException;
import io.github.cnsukidayo.wword.model.bo.JsonWordBO;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordId;
import io.github.cnsukidayo.wword.model.entity.WordStructure;
import io.github.cnsukidayo.wword.model.enums.DivideType;
import io.github.cnsukidayo.wword.model.exception.ResultCodeEnum;
import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.github.cnsukidayo.wword.model.params.UpLoadWordJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final WordStructureService wordStructureService;

    private final WordMapper wordMapper;

    private final WordIdMapper wordIdMapper;

    private final CoreFeignClient coreFeignClient;

    private static final String EXPAND = "expand";

    private final Comparator<WordId> wordIdComparator;

    public WordHandleServiceImpl(WordStructureService wordStructureService,
                                 WordMapper wordMapper,
                                 WordIdMapper wordIdMapper,
                                 ObjectMapper objectMapper,
                                 CoreFeignClient coreFeignClient,
                                 @Qualifier("wordIdComparator") Comparator<WordId> wordIdComparator) {
        this.wordStructureService = wordStructureService;
        this.wordMapper = wordMapper;
        this.wordIdMapper = wordIdMapper;
        this.objectMapper = objectMapper;
        this.coreFeignClient = coreFeignClient;
        this.wordIdComparator = wordIdComparator;
    }

    @Async
    @Transactional
    @Override
    public void handleJson(UpLoadWordJson upLoadWordJson, InputStream jsonInputStream) {
        Assert.notNull(upLoadWordJson, "upLoadWordJson must not be null");
        Assert.notNull(jsonInputStream, "jsonInputStream must not be null");

        // 先获得单词对应的父划分,划分必须是父划分不能是子划分
        Divide divide = Optional.ofNullable(coreFeignClient.selectById(upLoadWordJson.getDivideId()))
            .filter(test -> test.getParentId() == -1)
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS.getCode(),
                "指定划分不存在!"));
        // 得到划分对应的id
        Long divideId = divide.getId();
        // 得到划分所对应的语种id
        Long languageId = divide.getLanguageId();
        // 根据语种id得到单词对应的结构体信息,并将其封装为K-V形式.Key是属性名称,value是属性名称对应的id
        Map<String, Long> wordStructureMap = Optional.ofNullable(wordStructureService.get(languageId))
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.NOT_EXISTS.getCode()
                , "当前语种还没有定义结构体信息!"))
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
                        groupId = tempWord.getGroupFlag();
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
                                Long choiceIndexId = tempWord.getGroupFlag();
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
                        Long groupId = tempWord.getGroupFlag();
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
                        Long groupId = tempWord.getGroupFlag();
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
                        Long groupId = tempWord.getGroupFlag();
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
                        Long groupId = tempWord.getGroupFlag();
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
            throw new BadRequestException(ResultCodeEnum.File_OPERATION.getCode(),
                "json文件处理异常");
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
                Long wordIdValue = Optional.ofNullable(wordIdMapper.selectOne(wordIdQuery)).orElseThrow(() -> new BadRequestException(ResultCodeEnum.ILLEGAL_STATE.getCode(),
                    "can not find word" + wordOrigin)).getId();
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
                        Long groupId = tempWord.getGroupFlag();
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
                            Long internalGroupId = tempWord.getGroupFlag();
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
                        Long groupId = tempWord.getGroupFlag();
                        // 得到同根词的内容
                        for (JsonWordBO.Content.Word.InternalContent.RelWord.InternalRelWord.InternalWord relWordInfo : relWord.getWords()) {
                            String hwd = relWordInfo.getHwd();
                            tempWord = createWord(wordIdValue);
                            tempWord.setGroupId(groupId);
                            tempWord.setWordStructureId(wordStructureMap.get("relWord"));
                            tempWord.setValue(hwd);
                            wordMapper.insert(tempWord);
                            Long internalGroupId = tempWord.getGroupFlag();
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
            throw new BadRequestException(ResultCodeEnum.File_OPERATION.getCode(),
                "json文件处理异常");
        }

        log.info("compute divide [{}] complete; consume time:[{}] ms", divide.getName(), System.currentTimeMillis() - startTime);
    }

    @Override
    public void saveWord(AddOrUpdateWordParam addOrUpdateWordParam) {
    }

    @Override
    @Async
    public void updateBase(Long divideId) {
        // todo 分布式事务目前无法解决
        /*
        本地事务,在分布式系统,只能控制自已的回滚,控制不了其它服务的回滚
         */
        // 首先查询出所有父划分
        List<Divide> officialDivideList = coreFeignClient.listParentDivide();
        // 得到总库的id
        Long baseId = officialDivideList.stream()
            .filter(divide -> divide.getDivideType() == DivideType.BASE)
            .map(Divide::getId)
            .findFirst()
            .orElseThrow(() -> new BadRequestException(ResultCodeEnum.ILLEGAL_STATE, "找不到总库!"));

        /*
            1.遍历每个划分,开始晋升到总库;先根据划分id查询到当前划分下的所有单词
            2.然后挨个查找数据库中的所有单词,但是只查询划分id在当前id之后的单词
            3.根据wordId从word表中查询出单词的所有详细信息
            4.晋升规则:先按照单词的结构数量越多的价值越高.在结构数量相同的情况下,信息越丰富的价值越高
         */

        Divide officialDivide = null;
        for (Divide divide : officialDivideList) {
            if (divide.getId().equals(divideId)) {
                officialDivide = divide;
                break;
            }
        }

        log.info("handler divide:[{}]", officialDivide.getName());
        Long officialDivideId = officialDivide.getId();
        // 根据划分id查询当前的所有单词
        List<WordId> currentWordIdList = coreFeignClient.selectWordIdByDivideId(officialDivideId);
        List<WordId> addToBaseList = new LinkedList<>();
        for (int i = 0; i < currentWordIdList.size(); i++) {
            WordId currentWordId = currentWordIdList.get(i);
            log.debug("current handle:[{}],word:[{}],index:[{}]",
                officialDivide.getName(),
                currentWordId.getWord(),
                i);
            // 如果当前单词已经存在于总库中则跳过
            if ( coreFeignClient.exist(currentWordId.getWord(), baseId)){
                continue;
            }
            // 直接根据word原文查询(但是divideId)必须比当前大
            List<WordId> sameWordIdList = coreFeignClient.selectSameWordIdWord(currentWordId);
            sameWordIdList.add(currentWordId);
            sameWordIdList.sort(wordIdComparator);
            addToBaseList.add(sameWordIdList.get(sameWordIdList.size() - 1));
        }
        // 批量添加单词
        for (WordId addWord : addToBaseList) {
            // 插入新单词
            WordId newWordId = new WordId();
            newWordId.setDivideId(baseId);
            newWordId.setWord(addWord.getWord());
            newWordId = coreFeignClient.saveWordId(newWordId);
            log.debug("saveWordId:[{}]", newWordId);
            // 查询出目标单词的详细信息
            List<Word> targetWordDetailList = coreFeignClient.selectWordById(addWord.getId());
            // 有限处理groupId是空的单词
            Queue<Word> queue = new ArrayDeque<>();
            targetWordDetailList.forEach(word -> {
                if (word.getGroupId() == null) {
                    queue.add(word);
                }
            });
            while (!queue.isEmpty()) {
                Word targetWordDetail = queue.poll();
                List<Word> childWordList = findChildWord(targetWordDetail, targetWordDetailList);
                Word word = new Word();
                word.setWordId(newWordId.getId());
                word.setWordStructureId(targetWordDetail.getWordStructureId());
                word.setValue(targetWordDetail.getValue());
                word.setGroupId(targetWordDetail.getGroupId());
                // 插入单词
                word = coreFeignClient.saveWord(word);
                log.debug("saveWord:[{}]", word.getValue());
                // 更新
                Long groupFlag = word.getGroupFlag();
                childWordList.forEach(childWord -> {
                    childWord.setGroupId(groupFlag);
                    queue.add(childWord);
                });
            }
        }
        // 发布到elastic search
        log.info("updateBase finish");
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
            throw new BadRequestException(ResultCodeEnum.ILLEGAL_STATE.getCode(),
                "当前语种的结构体中缺少必要字段" + Arrays.toString(notExistList.toArray()) + "如果不存在会造成数据不一致问题.");
        }
    }

    /**
     * 查询出和当前单词有关系的组单词
     *
     * @param word 当前单词
     * @return 返回有单词的集合
     * @@param wordList 所有单词
     */
    private List<Word> findChildWord(Word word, List<Word> wordList) {
        List<Word> childList = new LinkedList<>();
        Long group = word.getGroupFlag();
        for (Word childWord : wordList) {
            if (childWord.getGroupId() != null && word.getGroupFlag().equals(childWord.getGroupId())) {
                childList.add(childWord);
            }
        }
        return childList;
    }


}
