package com.professionalstrangers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

/**
 * Main entry point for production builds.
 */
@SpringBootApplication
@Profile("production")
public class ProfessionalStrangersApplicationWAR extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProfessionalStrangersApplicationWAR.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProfessionalStrangersApplicationWAR.class, args);
    }
}
