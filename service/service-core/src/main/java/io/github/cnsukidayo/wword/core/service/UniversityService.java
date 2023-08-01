package io.github.cnsukidayo.wword.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.University;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/26 15:45
 */
public interface UniversityService extends IService<University> {
    /**
     * 根据学校的名称查询学校
     *
     * @param schoolName 参数不为null且不为空
     * @return 返回学校集合
     */
    List<University> getByName(String schoolName);

    /**
     * 判断数据库中是否存在某个大学
     *
     * @param schoolName 参数不为null且不为空
     */
    boolean hasUniversity(String schoolName);
}
