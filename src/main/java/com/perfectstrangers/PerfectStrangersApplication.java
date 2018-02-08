package com.perfectstrangers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile({"dev", "production"})
public class PerfectStrangersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerfectStrangersApplication.class, args);
    }
}
