package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.pojo.Divide;
import io.github.cnsukidayo.wword.model.pojo.DivideWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/28 16:16
 */
@Repository
public interface DivideMapper extends BaseMapper<Divide> {
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

}
