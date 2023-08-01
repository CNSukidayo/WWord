package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.DivideWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/29 16:41
 */
@Repository
public interface DivideWordMapper extends BaseMapper<DivideWord> {

    /**
     * 批量添加单词到一个子划分中
     *
     * @param divideId 子划分id不为null
     * @param list     单词列表不为null
     * @param UUID     用户id不为null
     */
    void insertBatchDivideWord(@Param("divideId") Long divideId, @Param("divideWordList") List<DivideWord> list, @Param("uuid") Long UUID);

    /**
     * 批量删除一个子划分中的所有单词
     *
     * @param divideId   子划分id不为null
     * @param wordIdList 单词Id列表不为null
     * @param UUID       用户id不为null
     */
    void deleteDivideWord(@Param("divideId") Long divideId, @Param("wordIdList") List<Long> wordIdList, @Param("uuid") Long UUID);

    /**
     * 拷贝子划分id下的所有单词
     *
     * @param sourceId 原sourceId不为null
     * @param targetId 目标divideId不为null
     * @param UUID     用户id不为null
     */
    void copy(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId, @Param("uuid") Long UUID);
}
