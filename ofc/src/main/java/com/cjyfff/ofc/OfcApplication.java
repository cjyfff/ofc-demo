package com.cjyfff.ofc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OfcApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfcApplication.class, args);
    }

}
