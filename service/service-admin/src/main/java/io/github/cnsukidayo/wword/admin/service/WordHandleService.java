package io.github.cnsukidayo.wword.admin.service;

import io.github.cnsukidayo.wword.model.params.AddOrUpdateWordParam;
import io.github.cnsukidayo.wword.model.params.UpLoadWordJson;

/**
 * @author sukidayo
 * @date 2023/7/31 10:12
 */
public interface WordHandleService {

    /**
     * 处理enwords数据库数据
     */
    void handleEnWords();

    /**
     * 处理丰富的json信息
     *
     * @param upLoadWordJson 上传的单词Json内容
     */
    void handleJson(UpLoadWordJson upLoadWordJson);

    /**
     * 添加或更新一个单词(注意该单词是添加到一个划分中,是直接添加到一个父划分中,而不是子划分)
     *
     * @param addOrUpdateWordParam 单词对象不能为null
     */
    void saveWord(AddOrUpdateWordParam addOrUpdateWordParam);
}
