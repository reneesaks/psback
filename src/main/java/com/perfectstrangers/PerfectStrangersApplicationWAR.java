package com.perfectstrangers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

/**
 * Main entry point for deployment builds.
 */
@SpringBootApplication
@Profile("deployment")
public class PerfectStrangersApplicationWAR extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PerfectStrangersApplicationWAR.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PerfectStrangersApplicationWAR.class, args);
    }
}
