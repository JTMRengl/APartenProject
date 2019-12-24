package com.offcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserProvdier01Application {
    public static void main(String[] args) {
        SpringApplication.run( UserProvdier01Application.class, args);
    }

}
