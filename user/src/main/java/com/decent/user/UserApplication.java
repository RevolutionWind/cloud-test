package com.decent.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sunxy
 * @date 2020/12/18 10:38
 */
@EnableFeignClients(basePackages = "com.decent.**.client")
@SpringCloudApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
