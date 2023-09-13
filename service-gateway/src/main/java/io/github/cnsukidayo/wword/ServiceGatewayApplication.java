package io.github.cnsukidayo.wword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * @author sukidayo
 * @date 2023/9/6 19:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableReactiveFeignClients
public class ServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGatewayApplication.class);
    }

}
