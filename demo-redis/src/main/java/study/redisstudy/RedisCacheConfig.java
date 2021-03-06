package study.redisstudy;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisCacheConfig {

  @Bean(name = "redisCacheManager")
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
      .cacheDefaults(defaultConf())
      .withInitialCacheConfigurations(confMap())
      .build();
  }

  private RedisCacheConfiguration defaultConf() {
    return RedisCacheConfiguration.defaultCacheConfig()
      .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
      .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()))
      .entryTtl(Duration.ofMinutes(1));
  }

  private Map<String, RedisCacheConfiguration> confMap() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    cacheConfigurations.put("cacheName1", defaultConf().entryTtl(Duration.ofMinutes(30L)));
    cacheConfigurations.put("cacheName2", defaultConf().entryTtl(Duration.ofMinutes(600L)));
    return cacheConfigurations;
  }
}