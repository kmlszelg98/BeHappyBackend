package com.behappy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Bartosz on 25.11.2016.
 */
@Configuration
@EnableWebSecurity
class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    static final String SECRET = "ps#txGFB*2hUD2%-DEp$r!xn+F?$FrASmZqb";

    public SpringSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .authorizeRequests()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/users").permitAll()
                .antMatchers("/api/users/confirm/**").permitAll()
                .antMatchers("/api/users/invite/**").permitAll()
                .antMatchers("/api/users/remind").permitAll()
                .antMatchers("/api/users/edit/**").permitAll()
                .antMatchers("/api/users/invitation/**").permitAll()
                .anyRequest().permitAll().and() //TODO BACK TO AUTH!!!
                .addFilterBefore(statelessAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public StatelessAuthenticationFilter statelessAuthenticationFilter() {
        return new StatelessAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService(SECRET);
    }
}
