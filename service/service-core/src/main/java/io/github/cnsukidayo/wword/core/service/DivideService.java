package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.DivideWord;
import io.github.cnsukidayo.wword.model.entity.LanguageClass;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.params.WordIdFromOtherParam;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 15:23
 */
public interface DivideService extends IService<Divide> {

    /**
     * 查询所有语言分类
     *
     * @return 返回语言
     */
    List<LanguageClass> listLanguage();

    /**
     * 查询某个语种的所有官方划分
     *
     * @param languageId 语种id不为null
     * @param uuid       用户id不为null
     * @return 返回所有划分的集合
     */
    List<DivideDTO> listDivide(Long languageId, Long uuid);

    /**
     * 添加一个划分
     *
     * @param addDivideParam 划分参数不为null
     * @param uuid           用户id不为null
     */
    void save(AddDivideParam addDivideParam, Long uuid);

    /**
     * 删除一个划分
     *
     * @param id   划分id不为null
     * @param uuid 用户uuid不为null
     */
    void remove(Long id, Long uuid);

    /**
     * 批量定义单词到一个父划分中<br>
     * 首先需要明确的一个设计,不同的父划分之间的单词可以是隔离的,什么意思?<br>
     * 即同一个单词它可以有不同的解释,例如miss;在高中划分中它的意思可以是思念<br>
     * 而在大学划分中它的意思可以是思念、错过<br>
     * 所以一个父划分下所有子划分的单词必须来源于当前父划分<br>
     */
    void batchDefineWord();

    /**
     * 该方法的效果类似于{@link DivideService#batchDefineWord()}<br>
     * 但是当前方法允许从另外一个父划分中得到一个单词的信息给到当前父划分
     *
     * @see DivideService#batchDefineWord()
     */
    void batchDefineWordFromOtherDivide(WordIdFromOtherParam wordIdFromOtherParam, Long uuid);

    /**
     * 批量添加单词到一个子划分中
     *
     * @param divideId   子划分id不为null
     * @param wordIdList 单词id列表不为null
     * @param UUID       用户id不为null不为null
     */
    void saveBatchDivideWord(Long divideId, List<Long> wordIdList, Long UUID);

    /**
     * 批量删除一个子划分中的单词
     *
     * @param divideId   划分id不为null
     * @param wordIdList 单词列表不为null
     * @param UUID       用户id不为null
     */
    void deleteDivideWord(Long divideId, List<Long> wordIdList, Long UUID);

    /**
     * 展示子划分下面的所有单词(摘要信息)
     *
     * @param divideIds 子划分的id列表
     * @return 单词集合
     */
    List<DivideWord> listDivideWord(List<Long> divideIds);

    /**
     * 展示子划分下面的所有单词(详细信息)
     *
     * @param divideIds 子划分的id列表
     * @return 单词集合 K:单词的id V:单词的详细信息
     */
    List<Word> listWordByDivideId(List<Long> divideIds);

    /**
     * 拷贝一个划分
     *
     * @param divideId 收藏夹id不为null
     * @param uuid     拷贝的目标用户id不为null
     */
    void copyDivide(Long divideId, Long uuid);

    /**
     * 查询出所有的父划分,并且会按照divideID进行升序
     *
     * @return 返回集合不为空nul
     */
    List<Divide> listParentDivide();

}
