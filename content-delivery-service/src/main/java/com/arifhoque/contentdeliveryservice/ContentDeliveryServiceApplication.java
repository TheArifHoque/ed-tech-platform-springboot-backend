package com.arifhoque.contentdeliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.arifhoque")
public class ContentDeliveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentDeliveryServiceApplication.class, args);
    }

}
