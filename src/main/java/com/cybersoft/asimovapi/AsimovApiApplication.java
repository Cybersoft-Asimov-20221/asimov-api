package com.cybersoft.asimovapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AsimovApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsimovApiApplication.class, args);
    }

}
