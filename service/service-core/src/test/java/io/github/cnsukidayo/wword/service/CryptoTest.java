package io.github.cnsukidayo.wword.service;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/7/25 14:41
 */
public class CryptoTest {

    @Test
    public void testHutoolCrypto() {
        // 加盐加密
        String hashpw = BCrypt.hashpw("123456789");
        System.out.println(BCrypt.checkpw("123456789", hashpw));
    }

}
