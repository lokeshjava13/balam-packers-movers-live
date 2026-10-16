package com.balam.packers_movers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.balam")
@EnableJpaRepositories(basePackages = "com.balam.repository")
@EntityScan(basePackages = "com.balam.entity")
public class PackersMoversApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackersMoversApplication.class, args);
    }
}