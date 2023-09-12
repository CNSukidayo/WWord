package io.github.cnsukidayo.wword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 取消数据源自动配置
 *
 * @author sukidayo
 * @date 2023/9/10 22:54
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableDiscoveryClient
//@EnableFeignClients
public class ServiceThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceThirdPartyApplication.class, args);
    }

}