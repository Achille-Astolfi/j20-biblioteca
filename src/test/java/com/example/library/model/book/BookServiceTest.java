package com.example.library.model.book;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;
import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
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

  //per il metodo createBookTest
  @Autowired
  AuthorRepository authorRepository;
  //anche in questo caso unit=component
  @Autowired
  private BookService bookService;
  //in realtà se non creiamo l'implementazione farlocca di BookMapper
  //la unit è in realtà BookServiceImpl+BookMapperImpl
  @Test
  @Order(1)
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
  @Order(0)
  void createBookTest() {
    //sono obbligato a creare un record nella tabella author
    //altrimenti il test non può essere eseguito
    Author author = new Author();
    author.setFirstName("J. K.");
    author.setLastName("Rowling");
    this.authorRepository.save(author);
    //non ho la possibilità di testare se l'autore è corretto
    //mi accontento di testare che la property author dell'output
    //non sia null
    BookCreateDto input = new BookCreateDto();
    input.setTitle("Harry Potter e la camera dei segreti");
    input.setAuthorId(1L);
    BookResource output = this.bookService.createBook(input);
    //mi aspetto che l'output sia non nullo
    assertNotNull(output);
    assertEquals(input.getTitle(), output.getTitle());
    assertNotNull(output.getAuthor());
  }

  @Test
  @Order(1)
  void createBookNoTitleTest() {
    //metto Order(i) perché ho bisogno di avere un record dentro la tabella author: questo record è creeato
    //nel metodo createBookTest(): questa è solo UNA delle solucioni possibili. Potrei anche
    //replicare le tre righe detro questo metodo o estrarre un metodo private da invocare sia in createBookTest
    //che in questo metodo
    //Creiamo un dto con l'autore senza il titolo(Mi aspetto DataIntegrityViolation)
    assertThrows(DataIntegrityViolationException.class, this::createBookNoTitleImpl);

  }

  private void createBookNoTitleImpl() {
    BookCreateDto dto = new BookCreateDto();
    dto.setAuthorId(1L);
    this.bookService.createBook(dto);
  }

  @Test
  void createBookNoAuthorTest() {
    assertThrows(InvalidDataAccessApiUsageException.class, this::createBookNoAuthorImpl);
  }

  private void createBookNoAuthorImpl() {
    BookCreateDto dto = new BookCreateDto();
    dto.setTitle("Il libro della Giungla");
    this.bookService.createBook(dto);
  }
}
