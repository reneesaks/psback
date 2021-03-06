package com.professionalstrangers.config;

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
            "https://199.247.13.206",                       // Front end IP HTTPS
            "https://professionalstrangers.com",            // Main domain HTTPS
            "https://www.professionalstrangers.com",        // Main domain www HTTPS
            "https://pstrangers.com",                       // Domain 1 HTTPS
            "https://www.pstrangers.com",                   // Domain 1 www HTTPS
            "https://profstrangers.com",                    // Domain 2 HTTPS
            "https://www.profstrangers.com",                // Domain 2 www HTTPS
            "https://profstrangersclub.com",                // Domain 3 HTTPS
            "https://www.profstrangersclub.com",            // Domain 3 www HTTPS
            "https://professionalstrangersclub.com",        // Domain 4 HTTPS
            "https://www.professionalstrangersclub.com",    // Domain 4 www HTTPS
            "https://profmeetingclub.com",                  // Domain 5 HTTPS
            "https://www.profmeetingclub.com",              // Domain 5 www HTTPS
            "https://profmeetclub.com",                     // Domain 6 HTTPS
            "https://www.profmeetclub.com",                 // Domain 6 www HTTPS
            "https://professionalsdinnerclub.com",          // Domain 7 HTTPS
            "https://www.professionalsdinnerclub.com",      // Domain 7 www HTTPS
            "https://144.202.77.179",                       // Back end IP HTTPS
            "https://profstrangers-api.eu",                 // Back end domain HTTPS
            "https://www.profstrangers-api.eu",             // Back end domain www HTTPS
            "http://localhost:4200"                         // Localhost HTTP
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
