package study.redisstudy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Slf4j
@SpringBootTest
@Testcontainers
public class TempTest {

  @Autowired
  BookService bookService;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  RedisTemplate<String, String> template;

  @Container
  private static DockerComposeContainer composeContainer =
    new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

  @BeforeEach
  public void deleteAll(){
    template.delete("*");
  }

  @Test
  @DisplayName("first 조회시에는 select 로그가 발생되고, again 조회시에는 select 로그가 발생하지 않는다.")
  public void test(){

    bookService.saveBook("1");
    bookService.saveBook("2");

    Book first = bookService.books("1");
    log.info("ㄴ select 로그가 발생됨");
    assertThat(first).isNotNull();
    assertThat(first.getName()).isEqualTo("1");

    Book again = bookService.books("1");
    log.info("ㄴ select 로그가 발생되지 않음");
    assertThat(again).isNotNull();
    assertThat(again.getName()).isEqualTo("1");

  }
}