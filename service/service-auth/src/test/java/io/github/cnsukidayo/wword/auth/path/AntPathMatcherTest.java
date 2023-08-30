package io.github.cnsukidayo.wword.auth.path;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

/**
 * @author sukidayo
 * @date 2023/8/29 22:06
 */
public class AntPathMatcherTest {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Test
    public void pathMatch() {
        System.out.println(antPathMatcher.match("/api/auth/{tes}", "/api/auth/1"));
    }

}
