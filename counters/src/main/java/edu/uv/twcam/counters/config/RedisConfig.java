package edu.uv.twcam.counters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@ComponentScan("edu.uv.twcam.counters")
public class RedisConfig{
   private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

   @Value("${REDIS_COUNTERS_SERVICE_HOST}")
   private String redishost;

   @Value("${REDIS_COUNTERS_SERVICE_PORT}")
   private int redisport;

   @Bean
   JedisConnectionFactory jedisConnectionFactory() {
      logger.debug("jedisConncectionFactory() called");

      JedisConnectionFactory jedisConFactory
         = new JedisConnectionFactory();
      jedisConFactory.setHostName(redishost);
      jedisConFactory.setPort(redisport);

      return jedisConFactory;
   }

   @Bean(name="template")
   public RedisTemplate<String, Integer> redisTemplate() {
      logger.debug("redisTemplate() called");
      final RedisTemplate<String, Integer> template = new RedisTemplate<String, Integer>();
      template.setConnectionFactory(jedisConnectionFactory());
      template.setValueSerializer(new GenericToStringSerializer<Integer>(Integer.class));
      return template;
   }
}
