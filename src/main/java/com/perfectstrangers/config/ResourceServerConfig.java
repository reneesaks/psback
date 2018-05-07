package com.perfectstrangers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Defines resource server security configuration across the platform.
 *
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
 */
@Configuration
@EnableResourceServer
@Profile({"staging", "production"})
public class ResourceServerConfig {

    /**
     * Security configuration for endpoints that are public and private.
     */
    @Configuration
    @Profile({"staging", "production"})
    public static class ResourceConfig extends ResourceServerConfigurerAdapter {

        private ResourceServerTokenServices resourceServerTokenServices;

        @Value("${security.jwt.resource-ids}")
        private String resourceIds;

        @Autowired
        @SuppressWarnings("SpringJavaAutowiringInspection")
        public ResourceConfig(ResourceServerTokenServices resourceServerTokenServices) {
            this.resourceServerTokenServices = resourceServerTokenServices;
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(resourceIds).tokenServices(resourceServerTokenServices);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers()
                    .and()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/public/**").permitAll()
                    .antMatchers("/api/private/**").authenticated();
        }
    }


}