package com.example.library.model.book;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;
import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.app.book.BookMapper;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {
  @Autowired
  private BookService bookService;
  // se non creiamo l'implementazione farlocca di Bookmapper
  // la nostra unit è in realtà BookServiceImpl + BookMapperImpl

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  @Order(1)
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
  @Order(0)
  void createBookTest() {
    //sono "obbligato" a creare un record dentro la tabella author altrimenti il test non può essere eseguito
    Author author = new Author();
    author.setLastName("Rowling");
    this.authorRepository.save(author);

    BookCreateDto inputDto = new BookCreateDto();
    inputDto.setTitle("Harry Potter e il secondo volume");
    inputDto.setAuthorId(1L);
    BookResource output = this.bookService.createBook(inputDto);
    assertNotNull(output);
    assertEquals(inputDto.getTitle(), output.getTitle());
    assertNotNull(output.getAuthor());
  }

  @Test
  @Order(1) //perchè ho bisogno di avere un record dentro la tabella author, questo record è creato nel method crateBooktest
  void createBookNoTitleTest() {
    assertThrows(DataIntegrityViolationException.class, this::createBookNoTitleTestImpl);
    // creaiamo un DTO con l'autore senza il titolo(mi aspetto dataIntegrityViolation)
  }

  private void createBookNoTitleTestImpl() {
    BookCreateDto inputDto = new BookCreateDto();
    inputDto.setAuthorId(1L);
    this.bookService.createBook(inputDto);
  }

  @Test
  void createBookNoAuthorTest() {
  // creaiamo un DTO sena l'autore con il titolo(mi aspetto dataIntegrityViolation)c
    assertThrows(InvalidDataAccessApiUsageException.class, this::createBookNoAuthorImpl);
  }
  private void createBookNoAuthorImpl() {
    //mi aspetto un null pointer
    BookCreateDto inputDto = new BookCreateDto();
    inputDto.setTitle("Nuovo libro");
    this.bookService.createBook(inputDto);
  }
}
