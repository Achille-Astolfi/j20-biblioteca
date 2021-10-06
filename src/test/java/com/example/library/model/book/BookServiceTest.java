package com.example.library.model.book;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
class BookServiceTest {

  //anche in questo caso unit=component
  @Autowired
  private BookService bookService;
  //in realtà se non creiamo l'implementazione farlocca di BookMapper
  //la unit è in realtà BookServiceImpl+BookMapperImpl
  @Test
  void readBookByIdTest() {
    Long inputId = 1L;
    Optional<BookResource> output = this.bookService.readBookById(inputId);
    assertTrue(output.isPresent());
    BookResource resource = output.get();
    assertEquals(inputId, resource.getId());
  }

  @Test
  void readBookByIdNotFoundTest() {
    Long inputId = 999L;
    Optional<BookResource> output = this.bookService.readBookById(inputId);
    assertFalse(output.isPresent());

  }

  @Test
  void createBookTest() {
    //non ho la possibilità di testare se l'autore è corretto
    //mi accontento di testare che la property author dell'output
    //non sia null
    BookCreateDto input = new BookCreateDto();
    input.setTitle("Harry Potter e la camera dei segreti");
    input.setAuthorId(2L);
    BookResource output = this.bookService.createBook(input);
    //mi aspetto che l'output sia non nullo
    assertNotNull(output);
    assertEquals(input.getTitle(), output.getTitle());
    assertNotNull(output.getAuthor());
  }

  @Test
  void createBookNoTitleTest() {

  }

  @Test
  void createBookNoAuthorTest() {

  }
}
