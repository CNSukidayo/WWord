package io.github.cnsukidayo.wword.common.request.interfaces.core;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.dto.DivideDTO;
import io.github.cnsukidayo.wword.model.dto.DivideWordDTO;
import io.github.cnsukidayo.wword.model.dto.LanguageClassDTO;
import io.github.cnsukidayo.wword.model.params.AddDivideParam;
import io.github.cnsukidayo.wword.model.support.BaseResponse;

import java.util.List;

/**
 * 划分列表管理接口
 *
 * @author sukidayo
 * @date 2023/10/25 17:01
 */
public interface DivideRequest {

    /**
     * 查询所有语种
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<LanguageClassDTO>>> listLanguage();

    /**
     * 查询某个人的所有划分
     *
     * @param languageId 语种id
     * @param uuid       用户id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<DivideDTO>>> listDivide(String languageId,
                                                              String uuid);

    /**
     * 查询某个子划分下中定义的所有单词
     *
     * @param divideId 划分id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<DivideWordDTO>>> listDivideWord(String divideId);

    /**
     * 添加一个划分
     *
     * @param addDivideParam 添加划分参数
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> saveDivide(AddDivideParam addDivideParam);

    /**
     * 删除一个划分
     *
     * @param id 划分的id,可以是父划分id也可以是子划分id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> remove(String id);

    /**
     * 批量添加单词到一个子划分中
     *
     * @param divideId   划分的id
     * @param wordIdList 单词id列表
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> saveDivideWord(String divideId,
                                                       List<Long> wordIdList);

    /**
     * 批量删除一个子划分中的单词
     *
     * @param divideId   划分的id
     * @param wordIdList 单词id列表
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> deleteDivideWord(String divideId,
                                                         List<Long> wordIdList);

    /**
     * 拷贝一个划分
     *
     * @param divideId 划分的id
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<Void>> copyDivide(String divideId);


}
