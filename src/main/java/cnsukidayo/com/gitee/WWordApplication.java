package cnsukidayo.com.gitee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author sukidayo
 * @date 2023/5/17 15:11
 */
@SpringBootApplication
@EnableCaching
public class WWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(WWordApplication.class, args);
    }

}
