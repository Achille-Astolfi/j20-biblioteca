package com.example.library.model.book;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;
import com.example.library.app.book.BookMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
class BookServiceTest {
  @Autowired
  private BookService bookService;
  // se non creiamo l'implementazione farlocca di Bookmapper
  // la nostra unit è in realtà BookServiceImpl + BookMapperImpl
  @Test
  void readBookByIdTest() {
    Long inputId = 1L;
    Optional<BookResource> output = this.bookService.readBookById((inputId));
    assertTrue(output.isPresent());
    BookResource resource = output.get();
    assertEquals(inputId, resource.getId());
  }

  @Test
  void readBookByIdNotFoundTest() {
    Long inputId = 0L;
    Optional<BookResource> output = this.bookService.readBookById((inputId));
    assertFalse(output.isPresent());
  }

  @Test
  void createBookTest() {
  }

  @Test
  void createBookNoTitleTest() {
  }

  @Test
  void createBookNoAuthorTest() {
  }
}
