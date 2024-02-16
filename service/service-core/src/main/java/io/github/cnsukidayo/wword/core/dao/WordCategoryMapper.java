package io.github.cnsukidayo.wword.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cnsukidayo.wword.model.entity.WordCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sukidayo
 * @date 2023/7/27 10:44
 */
@Repository
public interface WordCategoryMapper extends BaseMapper<WordCategory> {

    /**
     * 找到当前CategoryOrder值最大的那个WordCategory
     */
    WordCategory findOrderByCategoryOrderFirst(@Param("uuid") Long uuid);

}
