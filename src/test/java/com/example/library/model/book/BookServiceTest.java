package com.example.library.model.book;

import com.example.library.app.LibraryBoot;
import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/author.sql", "/book.sql"})
class BookServiceTest {
    // anche in questo caso unit = component (come punto di partenza)
    @Autowired
    private BookService bookService;
    // in realtà se non creiamo l'implementazione farlocca di BookMapper
    // la nostra unit diventa BookServiceImpl + BookMapperImpl

    // vedi sotto il metodo createBookTest
    @Autowired
    private AuthorRepository authorRepository;

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
        Long inputId = 9999L;
        Optional<BookResource> output = this.bookService.readBookById(inputId);
        assertFalse(output.isPresent());
    }

    @Test
    @Order(0)
    void createBookTest() {
        // sono "obbligato" a creare un record dentro la tabella author altrimenti
        // il test non può essere eseguito
        Author author = new Author();
        author.setLastName("Rowling");
        this.authorRepository.save(author);
        // non ho la possibilità (al momento) di testare l'autore corretto
        // potrei accontentarmi di testare che la property author dell'output
        // non sia null
        BookCreateDto inputDto = new BookCreateDto();
        inputDto.setTitle("Harry Potter e il secondo volume");
        inputDto.setAuthorId(1L);
        BookResource output = this.bookService.createBook(inputDto);
        assertNotNull(output);
        assertEquals(inputDto.getTitle(), output.getTitle());
        assertNotNull(output.getAuthor());
    }

    @Test
    @Order(1)
    void createBookNoTitleTest() {
        // metto @Order(1) perché ho bisogno di avere un record dentro la tabella author; questo record è creato
        // nel metodo createBookTest(); questa è solo UNA delle soluzioni possibili. Potrei anche replicare le tre
        // righe dentro questo metodo o estrarre un metodo private da invocare sia in createBookTest che in questo metodo
        assertThrows(DbActionExecutionException.class, this::createBookNoTitleImpl);
    }

    private void createBookNoTitleImpl() {
        // Creiamo un dto con l'autore senza il titolo (mi aspetto DataIntegrityViolation)
        BookCreateDto inputDto = new BookCreateDto();
        inputDto.setAuthorId(1L);
        this.bookService.createBook(inputDto);
    }

    @Test
    void createBookNoAuthorTest() {
        // può essere InvalidDataAccessApiUsageException se commento alcune righe di BookMapper
        assertThrows(DbActionExecutionException.class, this::createBookNoAuthorImpl);
    }

    private void createBookNoAuthorImpl() {
        // Creiamo un dto senza l'autore con il titolo (mi aspetto NullPointerException)
        BookCreateDto inputDto = new BookCreateDto();
        inputDto.setTitle("Un libro di autore ignoto");
        this.bookService.createBook(inputDto);
    }
}
