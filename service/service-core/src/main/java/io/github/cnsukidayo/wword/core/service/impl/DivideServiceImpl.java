package io.github.cnsukidayo.wword.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.common.exception.AddFailException;
import io.github.cnsukidayo.wword.common.exception.BadRequestException;
import io.github.cnsukidayo.wword.common.exception.NonExistsException;
import io.github.cnsukidayo.wword.core.dao.DivideMapper;
import io.github.cnsukidayo.wword.core.dao.DivideWordMapper;
import io.github.cnsukidayo.wword.core.dao.LanguageClassMapper;
import io.github.cnsukidayo.wword.core.service.DivideService;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.pojo.Divide;
import io.github.cnsukidayo.wword.model.pojo.DivideWord;
import io.github.cnsukidayo.wword.model.pojo.LanguageClass;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author sukidayo
 * @date 2023/7/28 16:16
 */
@Service
public class DivideServiceImpl extends ServiceImpl<DivideMapper, Divide> implements DivideService {

    private final LanguageClassMapper languageClassMapper;
    private final DivideWordMapper divideWordMapper;

    public DivideServiceImpl(LanguageClassMapper languageClassMapper,
                             DivideWordMapper divideWordMapper) {
        this.languageClassMapper = languageClassMapper;
        this.divideWordMapper = divideWordMapper;
    }

    @Override
    public List<LanguageClass> listLanguage() {
        return languageClassMapper.selectList(null);
    }

    @Override
    public List<DivideDTO> listDivide(Long languageId, Long UUID) {
        Assert.notNull(languageId, "languageId must not be null");
        Assert.notNull(UUID, "UUID must not be null");

        // 根据languageId和uuid查询到所有的父划分
        List<Divide> parentDivideList = Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<Divide>().eq(Divide::getUUID, UUID)
                        .eq(Divide::getLanguageId, languageId)
                        .eq(Divide::getParentId, -1)))
                .orElseGet(ArrayList::new);
        List<DivideDTO> result = new ArrayList<>(parentDivideList.size());
        parentDivideList.forEach(parentDivide -> {
            DivideDTO divideDTO = new DivideDTO().convertFrom(parentDivide);
            result.add(divideDTO);
            List<Divide> childDivideList = Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<Divide>()
                            .eq(Divide::getParentId, parentDivide.getId())))
                    .orElseGet(ArrayList::new);
            List<DivideDTO> childResult = new ArrayList<>(childDivideList.size());
            childDivideList.forEach(childDivide -> {
                DivideDTO e = new DivideDTO().convertFrom(childDivide);
                // 查询当前子分类下有多少单词数量
                e.setElementCount(divideWordMapper.selectCount(new LambdaQueryWrapper<DivideWord>().eq(DivideWord::getDivideId, e.getId())));
                childResult.add(e);
            });
            divideDTO.setChildDivideDTO(childResult);
            divideDTO.setElementCount((long) childResult.size());
        });
        return result;
    }

    @Override
    public void save(AddDivideParam addDivideParam, Long uuid) {
        Assert.notNull(addDivideParam, "addDivideParam must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        if (!assertCanSave(addDivideParam, uuid)) {
            throw new AddFailException();
        }

        Divide divide = addDivideParam.convertTo();
        divide.setUUID(uuid);
        baseMapper.insert(divide);

    }

    @Override
    public void remove(Long id, Long uuid) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        Divide divide = Optional.ofNullable(baseMapper.selectById(id)).orElseThrow(() -> new NonExistsException("划分不存在!"));
        if (!divide.getUUID().equals(uuid)) {
            throw new BadRequestException("删除失败");
        }
        // 如果当前是父划分则删除父划分和所有子划分
        if (divide.getParentId() == -1) {
            // 查询出所有的子划分的id
            List<Long> divideIdList = new ArrayList<>(Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<Divide>()
                            .eq(Divide::getParentId, id)))
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(Divide::getId)
                    .toList());
            // 删除所有子划分下面的所有单词
            if (!CollectionUtils.isEmpty(divideIdList)) {
                divideWordMapper.deleteBatchIds(divideIdList);
            }
            // 删除所有划分
            divideIdList.add(id);
            baseMapper.deleteBatchIds(divideIdList);
        } else {
            baseMapper.delete(new LambdaQueryWrapper<Divide>().eq(Divide::getId, id));
            divideWordMapper.deleteById(id);
        }

    }

    @Override
    public void saveBatchDivideWord(Long divideId, List<Long> wordIdList, Long UUID) {
        Assert.notNull(divideId, "divideId must not be null");
        Assert.notNull(wordIdList, "wordIdList must not be null");
        Assert.notNull(UUID, "UUID must not be null");

        Divide divide = Optional.ofNullable(baseMapper.selectOne(new LambdaQueryWrapper<Divide>().eq(Divide::getId, divideId))).orElseThrow(() -> new NonExistsException("划分不存在不存在"));
        if (divide.getParentId() == -1 || !divide.getUUID().equals(UUID)) {
            throw new AddFailException();
        }
        // todo 根据单词的id查询出所有单词的名称,做进一步的封装.事务、异步

        DivideWord divideWord0 = new DivideWord();
        divideWord0.setWordId(1L);
        divideWord0.setWordName("apple");
        DivideWord divideWord1 = new DivideWord();
        divideWord1.setWordId(2L);
        divideWord1.setWordName("banana");

        List<DivideWord> list = new ArrayList<>();
        list.add(divideWord0);
        list.add(divideWord1);

        divideWordMapper.insertBatchDivideWord(divideId, list, UUID);
    }

    @Override
    public void deleteDivideWord(Long divideId, List<Long> wordIdList, Long UUID) {
        Assert.notNull(divideId, "divideId must not be null");
        Assert.notNull(wordIdList, "wordIdList must not be null");
        Assert.notNull(UUID, "UUID must not be null");

        divideWordMapper.deleteDivideWord(divideId, wordIdList, UUID);
    }

    @Override
    public List<DivideWord> listDivideWord(Long divideId) {
        Assert.notNull(divideId, "divideId must not be null");

        return Optional.ofNullable(divideWordMapper.selectList(new LambdaQueryWrapper<DivideWord>().eq(DivideWord::getDivideId, divideId))).orElseGet(ArrayList::new);
    }

    @Override
    public void copyDivide(Long divideId, Long uuid) {
        Assert.notNull(divideId, "divideId must not be null");
        Assert.notNull(uuid, "uuid must not be null");

        Divide parentDivide = Optional.ofNullable(baseMapper.selectById(divideId)).filter(divide -> divide.getParentId() == -1 && divide.getUUID() != 1).orElseThrow(() -> new NonExistsException("收藏失败"));
        // 拷贝父划分
        baseMapper.copy(parentDivide, uuid, -1L);
        // 查询出所有待拷贝的划分id
        List<Divide> childDivideList = Optional.ofNullable(baseMapper.selectList(new LambdaQueryWrapper<Divide>().eq(Divide::getParentId, divideId))).orElseGet(ArrayList::new);
        if (!CollectionUtils.isEmpty(childDivideList)) {
            // 拷贝所有子划分
            childDivideList.forEach(childDivide -> {
                Long sourceId = childDivide.getId();
                baseMapper.copy(childDivide, uuid, parentDivide.getId());
                divideWordMapper.copy(sourceId, childDivide.getId(), uuid);
            });
        }
    }

    /**
     * 判断当前这个划分是否允许被添加
     *
     * @param addDivideParam 参数不为null
     * @param UUID           参数不为null
     * @return 返回是否允许被添加
     */
    private boolean assertCanSave(AddDivideParam addDivideParam, Long UUID) {
        Assert.notNull(addDivideParam, "parentId must not be null");
        // 表示当前划分是一个语种划分,语种的约束交给数据库保障
        if (addDivideParam.getParentId() == -1) {
            return true;
        }
        // 查询父划分,如果父划分存在;并且父划分的parentId = -1则,代表当前是语种划分下的一个子划分.允许插入
        Divide divide = baseMapper.selectOne(new LambdaQueryWrapper<Divide>().eq(Divide::getId, addDivideParam.getParentId()));
        // 如果父划分不存在,则不可插入;即指向的parentId失效.
        return divide != null && divide.getParentId() == -1 && divide.getLanguageId().equals(addDivideParam.getLanguageId()) && divide.getUUID().equals(UUID);
    }

}
