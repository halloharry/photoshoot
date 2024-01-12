package com.photo.master.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "com.photo.master.user",
        "com.photo.auth.security"
}, lazyInit = true)
@EntityScan(basePackages = {
        "com.photo.master.data.model"
})
//@EnableJpaRepositories(basePackages = {
//        "com.photo.master.data.dao"
//})
@EnableJpaRepositories
//@EnableJpaAuditing
@SpringBootApplication
public class PhotoshootMasterUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhotoshootMasterUserApplication.class);
    }
}
