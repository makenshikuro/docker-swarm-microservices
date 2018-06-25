package edu.uv.twcam.login.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class BeanConfig{                
   private final Logger log = LoggerFactory.getLogger(BeanConfig.class);        

   @Bean 
   public Gson gson(){
      log.debug("Gson instance requested");
      return new GsonBuilder().create();
   }

   @Bean
   public BCryptPasswordEncoder bCryptPasswordEncoder() {
      log.debug("BCryptPasswordEncoder instance requested");
      return new BCryptPasswordEncoder();
   }
}
