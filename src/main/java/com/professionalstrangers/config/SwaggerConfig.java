package com.professionalstrangers.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"staging", "production"})
public class SwaggerConfig {

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    /**
     * A builder which is intended to be the primary interface into the swagger-springmvc framework. Provides
     * sensible defaults and convenience methods for configuration.
     *
     * @return Docket
     */
    @Bean
    public Docket pub() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.professionalstrangers.controller.pub"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket priv() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("private")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.professionalstrangers.controller.priv"))
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securitySchema()))
                .apiInfo(apiInfo());
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .build();
    }


    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = newArrayList();
        List<GrantType> grantTypes = newArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");
        grantTypes.add(passwordCredentialsGrant);
        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");
        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }

    /**
     * Sets API info in Swagger UI.
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Professional Strangers API",
                "Endpoints for Professional Strangers. \n" +
                        "Front end: https://github.com/kasparsuvi1/psfront \n" +
                        "Back end: https://github.com/reneesaks/psback \n",
                "0.1",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new Contact(
                        "Renee Säks",
                        "https://www.github.com/reneesaks",
                        "renee.saks1@gmail.com"),
                "Swagger license",
                "https://swagger.io/license/",
                Collections.emptyList());
    }
}