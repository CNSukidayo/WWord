package io.github.cnsukidayo.wword.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cnsukidayo.wword.auth.dao.UserMapper;
import io.github.cnsukidayo.wword.auth.service.UserService;
import io.github.cnsukidayo.wword.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author sukidayo
 * @date 2023/9/7 18:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
