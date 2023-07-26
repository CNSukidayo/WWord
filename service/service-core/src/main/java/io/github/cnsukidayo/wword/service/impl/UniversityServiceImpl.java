package io.github.cnsukidayo.wword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.dao.UniversityMapper;
import io.github.cnsukidayo.wword.pojo.University;
import io.github.cnsukidayo.wword.service.UniversityService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service层不返回VO和DTO,只返回pojo
 *
 * @author sukidayo
 * @date 2023/7/26 15:49
 */
@Service
public class UniversityServiceImpl extends ServiceImpl<UniversityMapper, University> implements UniversityService {

    @Override
    public List<University> getByName(String schoolName) {
        Assert.hasText(schoolName, "schoolName must has text");
        return Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<University>().like(University::getSchoolName, schoolName))).orElseGet(ArrayList::new);
    }

    @Override
    public boolean hasUniversity(String schoolName) {
        Assert.hasText(schoolName, "schoolName must has text");
        return baseMapper.selectCount(new LambdaQueryWrapper<University>().eq(University::getSchoolName, schoolName)) > 0;
    }

}
