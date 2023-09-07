package io.github.cnsukidayo.wword.service.impl;

import io.github.cnsukidayo.wword.model.environment.WWordConst;
import org.junit.Test;

import java.util.UUID;

/**
 * @author sukidayo
 * @date 2023/7/25 21:25
 */
public class UserServiceImplTest {

    @Test
    public void UUIDTest() {

        System.out.println(WWordConst.USER_NICK_PREFIX + UUID.randomUUID().toString().substring(0, 8));


    }

    @Test
    public void LocalDateTest() {
        String date = "2022-9-5";


    }

}