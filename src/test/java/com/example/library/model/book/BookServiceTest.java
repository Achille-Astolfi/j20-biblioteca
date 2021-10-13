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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    void createBookNoTitleTest() {

    }

    @Test
    void createBookNoAuthorTest() {

    }
}
