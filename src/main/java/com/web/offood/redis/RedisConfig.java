package com.web.offood.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
  /** The Redis cluster host name. */
  @Value("${aws.REDIS_CLUSTER_ENDPOINT_HOSTNAME}")
  private String redisHost = null;

  /** The Redis cluster port. */
  @Value("${aws.REDIS_CLUSTER_ENDPOINT_PORT}")
  private int redisPort = 0;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    // Tạo Standalone Connection tới Redis
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
  }

  @Bean
  @Primary
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);

    return template;
  }
}
