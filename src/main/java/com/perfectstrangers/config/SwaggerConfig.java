package com.perfectstrangers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.perfectstrangers.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Perfect Strangers API",
                "Endpoints for Perfect Strangers. \n" +
                        "Front end: https://github.com/kasparsuvi1/psfront \n" +
                        "Back end: https://github.com/reneesaks/<project-name-here> \n",
                "0.1",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new Contact(
                        "Renee Säks",
                        "https://www.github.com/reneesaks",
                        "renee.saks1@gmail.com"),
                "Swagger license",
                "https://swagger.io/license/",
                Collections.emptyList());
        return apiInfo;
    }
}
