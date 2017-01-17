package com.learn.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    /*
    -> We Define our security policy here
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .permitAll();
    }


    @Autowired
    public void configureInMemoryUsers(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user1").password("pass").roles("ADMIN","USER")
                .and()
                .withUser("user2").password("pass").roles("USER")
                .and()
                .withUser("baduser1").password("pass").roles("USER").accountLocked(true)
                .and()
                .withUser("baduser2").password("pass").roles("USER").disabled(true);

    }
}
