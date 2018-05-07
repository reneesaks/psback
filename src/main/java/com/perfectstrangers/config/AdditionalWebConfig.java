package com.perfectstrangers.config;

import java.util.Arrays;
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
@Profile({"production", "deployment"})
public class AdditionalWebConfig {

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
        config.addAllowedOrigin("https://199.247.13.206");
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
