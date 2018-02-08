package com.perfectstrangers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@Profile({"production", "deployment"})
public class ResourceServerConfig {

    @Configuration
    @Profile({"production", "deployment"})
    @Order(1)
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
                    .authorizeRequests()
                    .antMatchers("/api/public/**")
                    .permitAll()
                    .antMatchers("/api/private/**")
                    .authenticated();
        }
    }

    @Configuration
    @Profile({"production", "deployment"})
    @Order(2)
    public static class SwaggerSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/swagger-ui.html")
                    .antMatcher("/v2/api-docs")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("DEVELOPER")
                    .and()
                    .httpBasic();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // Create a developer account
            auth.inMemoryAuthentication()
                    .withUser("dev")
                    .password("password")
                    .roles("DEVELOPER");
        }
    }
}
