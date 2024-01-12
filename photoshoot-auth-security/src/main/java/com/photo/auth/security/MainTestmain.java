package com.photo.auth.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "com.photo.auth.security"
}, lazyInit = true)
@EntityScan(basePackages = {
        "com.photo.master.data.model"
})
@EnableJpaRepositories(basePackages = {
        "com.photo.master.data.dao"
})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class MainTestmain {
    public static void main(String[] args) {
        SpringApplication.run(MainTestmain.class);
    }
}
