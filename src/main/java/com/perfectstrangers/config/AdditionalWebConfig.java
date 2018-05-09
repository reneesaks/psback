package com.perfectstrangers.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Additional web configuration.
 */
@Configuration
@Profile({"staging", "production"})
public class AdditionalWebConfig {

    private static List<String> allowedOrigins = Arrays.asList(
            "https://199.247.13.206",   // Front end HTTPS
            "https://144.202.77.179",   // Back end HTTPS
            "http://localhost:4200"     // Localhost HTTP
    );

    private static List<String> allowedHeaders = Arrays.asList(
            "Authorization", "Content-Type"
    );

    private static List<String> allowedMethods = Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
    );

    /**
     * CORS configuration.
     *
     * @return FilterRegistrationBean.
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);
        source.registerCorsConfiguration("/**", config);

        CorsFilter corsFilter = new CorsFilter(source);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);

        bean.setOrder(0);
        return bean;
    }
}
