package io.github.cnsukidayo.wword.common.request.interfaces.auth;

import io.github.cnsukidayo.wword.common.request.ResponseWrapper;
import io.github.cnsukidayo.wword.model.support.BaseResponse;
import io.github.cnsukidayo.wword.model.vo.LoginLogVO;

import java.util.List;

/**
 * 日志记录接口
 *
 * @author sukidayo
 * @date 2023/10/25 21:07
 */
public interface LoggerRequest {

    /**
     * 查询用户一个月内的登陆记录;并且只查询50条记录;按照降序排序
     *
     * @return 返回值不为null
     */
    ResponseWrapper<BaseResponse<List<LoginLogVO>>> getLoginLog();


}
