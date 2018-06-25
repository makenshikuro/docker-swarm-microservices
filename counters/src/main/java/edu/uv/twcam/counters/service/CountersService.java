package edu.uv.twcam.counters.service;

import edu.uv.twcam.counters.config.*;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ValueOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;

@Service
@ComponentScan("edu.uv.twcam.counters.config")
public class CountersService{
   private static final Logger logger = LoggerFactory.getLogger(CountersService.class);

   @Autowired
   private RedisTemplate<String, Integer> template; // This can be RedisTemplate<String, Long> also based on your need
   
   @Resource(name="template")
   private ValueOperations<String, Integer> valueOps;

   public String increment(String key) {
      Integer v = valueOps.get(key);
      if (v==null)
         valueOps.set(key,0);
      String val = ((Long)valueOps.increment(key, 1l)).toString();      
      logger.debug("increment " + key + " called. Value from redis: " + val);
      return val;
   }  

   public String decrement(String key) {
      Integer v = valueOps.get(key);
      if (v==null)
         valueOps.set(key,0);         
      String val = ((Long)valueOps.increment(key, -1l)).toString();      
      logger.debug("increment " + key + " called. Value from redis: " + val);
      return val;
   }  

   public String get(String key) {
      Integer v = valueOps.get(key);
      if (v==null){
         valueOps.set(key,0);
         return "0";
      }
      else
         return v.toString();
   }  
}
