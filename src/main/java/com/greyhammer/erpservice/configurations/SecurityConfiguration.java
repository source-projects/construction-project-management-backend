package com.greyhammer.erpservice.configurations;

import com.greyhammer.erpservice.filters.AwsCognitoJwtAuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    AwsCognitoJwtAuthFilter awsCognitoJwtAuthFilter;

    public SecurityConfiguration(AwsCognitoJwtAuthFilter awsCognitoJwtAuthFilter) {
        this.awsCognitoJwtAuthFilter = awsCognitoJwtAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().cacheControl();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("**/health").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**").authenticated()
                //.anyRequest().authenticated()
                .and()
                .addFilterBefore(awsCognitoJwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }
}
