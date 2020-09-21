package study.redisstudy;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

  @CacheEvict(cacheNames = "books", key = "#name")
  public void saveBook(String name) {
    bookRepository.save(new Book(name));
  }

  @Cacheable(value = "books" , key = "#name")
  @Transactional
  public Book books(String name) {
    Book book = bookRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
    return book;
  }
}
