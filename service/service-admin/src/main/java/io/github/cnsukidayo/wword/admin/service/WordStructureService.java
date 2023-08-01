package io.github.cnsukidayo.wword.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.entity.WordStructure;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/7/30 14:29
 */
public interface WordStructureService extends IService<WordStructure> {

    /**
     * 格根据语种id获取单词的结构
     *
     * @param languageId 语种id不为null
     * @return 返回单词结构
     */
    List<WordStructure> get(Long languageId);
}
