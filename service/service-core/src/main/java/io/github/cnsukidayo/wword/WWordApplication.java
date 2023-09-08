package io.github.cnsukidayo.wword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sukidayo
 * @date 2023/5/17 15:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(WWordApplication.class, args);
    }

}
