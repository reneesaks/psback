package com.perfectstrangers.config;

import com.perfectstrangers.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Security configuration for JWT.
 *
 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile({"production", "deployment"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Value("${security.security-realm}")
    private String securityRealm;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
            AuthenticationEventPublisher authenticationEventPublisher) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationEventPublisher = authenticationEventPublisher;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * .authenticationEventPublisher(authenticationEventPublisher) required for listening on authentication
     * events
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationEventPublisher(authenticationEventPublisher)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(new ShaPasswordEncoder(encodingStrength));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName(securityRealm)
                .and()
                .csrf()
                .disable();

    }

    /**
     * Sets JwtAccessTokenConverter signing key.
     *
     * @return converter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    /**
     * A TokenStore implementation that just reads data from the tokens themselves. Not really a store since
     * it never persists anything, and methods like getAccessToken(OAuth2Authentication) always return null.
     * But nevertheless a useful tool since it translates access tokens to and from authentications. Use this
     * wherever a TokenStore is needed, but remember to use the same JwtAccessTokenConverter instance (or one
     * with the same verifier) as was used when the tokens were minted.
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * This is et to primary to avoid any accidental duplication with another token service instance of the
     * same name.
     *
     * @return DefaultTokenServices
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
