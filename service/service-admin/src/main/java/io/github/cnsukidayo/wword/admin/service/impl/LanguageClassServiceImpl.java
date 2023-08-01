package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.admin.dao.LanguageClassMapper;
import io.github.cnsukidayo.wword.admin.service.LanguageClassService;
import io.github.cnsukidayo.wword.model.params.AddLanguageClassParam;
import io.github.cnsukidayo.wword.model.params.UpdateLanguageClassParam;
import io.github.cnsukidayo.wword.model.entity.LanguageClass;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sukidayo
 * @date 2023/7/29 20:16
 */
@Service
public class LanguageClassServiceImpl extends ServiceImpl<LanguageClassMapper, LanguageClass> implements LanguageClassService {

    @Override
    public void save(AddLanguageClassParam addLanguageClassParam) {
        Assert.notNull(addLanguageClassParam, "addLanguageClassParam must not be null");

        LanguageClass languageClass = addLanguageClassParam.convertTo();
        baseMapper.insert(languageClass);

        // todo 当创建一个语种的时候自动添加一些语种的默认单词结构.包括音标和another(即无法划分的)
    }

    @Override
    public void update(UpdateLanguageClassParam updateLanguageClassParam) {
        Assert.notNull(updateLanguageClassParam, "updateLanguageClassParam must not be null");

        LanguageClass languageClass = updateLanguageClassParam.convertTo();
        baseMapper.updateById(languageClass);
    }


}
