package com.mladmin.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MlAdminPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MlAdminPortalApplication.class, args);
    }
}