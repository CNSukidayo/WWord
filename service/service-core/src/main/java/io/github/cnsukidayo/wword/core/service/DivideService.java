package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.DivideWord;
import io.github.cnsukidayo.wword.model.entity.LanguageClass;

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
     * 展示一个划分下面的所有单词
     *
     * @param divideId 划分的id
     * @return 单词集合
     */
    List<DivideWord> listDivideWord(Long divideId);

    /**
     * 拷贝一个划分
     *
     * @param divideId 收藏夹id不为null
     * @param uuid     拷贝的目标用户id不为null
     */
    void copyDivide(Long divideId, Long uuid);

    /**
     * 查询出所有的父划分
     *
     * @return 返回集合不为空nul
     */
    List<Divide> listParentDivide();
}
