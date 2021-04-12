package com.decent.integral;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sunxy
 * @date 2020/12/18 10:35
 */
@EnableFeignClients
@SpringCloudApplication
public class IntegralApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegralApplication.class, args);
    }
}
