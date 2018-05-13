package com.professionalstrangers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * Main entry point for developer and production runs/builds.
 */
@SpringBootApplication
@Profile({"dev", "staging"})
public class ProfessionalStrangersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfessionalStrangersApplication.class, args);
    }
}
