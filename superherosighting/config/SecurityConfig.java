/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

/**
 *
 * @author ctrop
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetails;

    /*  @Autowired
    public void configureGlobalInMemory(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("sidekick").password("{noop}password").roles("SIDEKICK")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN", "SIDEKICK");
    }

     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("admin").hasRole("ADMIN")
                .antMatchers("index").permitAll()
                .antMatchers("supervillains").permitAll()
                .antMatchers("sightings").permitAll()
                .antMatchers("superpowers").permitAll()
                .antMatchers("locations").permitAll()
                .antMatchers("organizations").permitAll()
                .antMatchers("createVillain").hasRole("SIDEKICK")
                .antMatchers("createVillain").hasRole("ADMIN")
                .antMatchers("createPower").hasRole("ADMIN")
                .antMatchers("createOrganizatoin").hasRole("ADMIN")
                .antMatchers("createLocation").hasRole("ADMIN")
                .antMatchers("createLocation").hasRole("SIDEKICK")
                .antMatchers("createSighting").hasRole("ADMIN")
                .antMatchers("createSighting").hasRole("SIDEKICK")
                .antMatchers("/css/**","/img/**", "/js/**", "/fonts/**").permitAll()
                .anyRequest().hasRole("SIDEKICK")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?login_error=1")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();

    }

    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
