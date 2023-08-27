package io.github.cnsukidayo.wword.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cnsukidayo.wword.common.config.properties.WWordProperties;
import io.github.cnsukidayo.wword.core.service.LoginLogService;
import io.github.cnsukidayo.wword.model.entity.LoginLog;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.vo.LoginLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sukidayo
 * @date 2023/8/27 14:48
 */
@Tag(name = "日志记录接口")
@RestController
@RequestMapping("api/u/logger")
public class LoggerController {

    private final LoginLogService loginLogService;

    private final WWordProperties wWordProperties;

    public LoggerController(LoginLogService loginLogService,
                            WWordProperties wWordProperties) {
        this.loginLogService = loginLogService;
        this.wWordProperties = wWordProperties;
    }

    @Operation(summary = "查询用户一个月内的登陆记录;并且只查询50条记录;按照降序排序")
    @GetMapping("getLoginLog")
    public List<LoginLogVO> getLoginLog(User user) {
        Page<LoginLog> pageParam = new Page<>(0, wWordProperties.getMaxLoginLog());
        IPage<LoginLog> pageModel = loginLogService.getLoginLog(user, pageParam);
        List<LoginLog> result = pageModel.getRecords();
        return result.stream()
                .map(loginLog -> {
                    LoginLogVO loginLogVO = new LoginLogVO().convertFrom(loginLog);
                    loginLogVO.setLoginTypeString(loginLogVO.getLoginType().getValue());
                    return loginLogVO;
                }).collect(Collectors.toList());
    }

}
