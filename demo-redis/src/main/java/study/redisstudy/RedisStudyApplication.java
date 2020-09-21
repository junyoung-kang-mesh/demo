package study.redisstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisStudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(RedisStudyApplication.class, args);
  }

}
