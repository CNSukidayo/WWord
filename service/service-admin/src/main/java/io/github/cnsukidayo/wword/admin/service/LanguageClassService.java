package io.github.cnsukidayo.wword.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cnsukidayo.wword.model.params.AddLanguageClassParam;
import io.github.cnsukidayo.wword.model.params.UpdateLanguageClassParam;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;

/**
 * @author sukidayo
 * @date 2023/7/29 20:15
 */
public interface LanguageClassService extends IService<LanguageClass> {
    /**
     * 添加一个语种
     *
     * @param addLanguageClassParam 添加新语种参数不为null
     */
    void save(AddLanguageClassParam addLanguageClassParam);

    /**
     * 更新一个语种的信息
     *
     * @param updateLanguageClassParam 更新的语种信息
     */
    void update(UpdateLanguageClassParam updateLanguageClassParam);
}
