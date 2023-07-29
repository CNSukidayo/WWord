package io.github.cnsukidayo.wword.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.admin.dao.LanguageClassMapper;
import io.github.cnsukidayo.wword.admin.service.LanguageClassService;
import io.github.cnsukidayo.wword.model.params.AddLanguageClassParam;
import io.github.cnsukidayo.wword.model.params.UpdateLanguageClassParam;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;
import org.springframework.beans.BeanUtils;
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

        LanguageClass languageClass = new LanguageClass();
        BeanUtils.copyProperties(addLanguageClassParam, languageClass);
        baseMapper.insert(languageClass);
    }

    @Override
    public void update(UpdateLanguageClassParam updateLanguageClassParam) {
        Assert.notNull(updateLanguageClassParam, "updateLanguageClassParam must not be null");

        LanguageClass languageClass = new LanguageClass();
        BeanUtils.copyProperties(updateLanguageClassParam, languageClass);
        baseMapper.updateById(languageClass);
    }


}
