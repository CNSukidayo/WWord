package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.Word;
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
     * 拷贝一个划分
     *
     * @param source   源划分不为null
     * @param UUID     目标用户id不为null
     * @param parentId 父id不为null
     */
    void copy(@Param("source") Divide source, @Param("uuid") Long UUID, @Param("parentId") Long parentId);

    List<Word> listWordByDivideId(@Param("divideIds") List<Long> divideIds);

}
