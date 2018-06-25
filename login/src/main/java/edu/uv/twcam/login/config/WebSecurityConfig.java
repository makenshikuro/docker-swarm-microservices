package edu.uv.twcam.login.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      // Allow /register and /login URLs
      http.cors().and().csrf().disable().authorizeRequests()
         .antMatchers(HttpMethod.POST, "/register").permitAll()
         .antMatchers(HttpMethod.POST, "/login").permitAll()
         .anyRequest().authenticated();
   }
}
