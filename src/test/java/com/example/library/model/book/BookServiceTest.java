package com.example.library.model.book;

import com.example.library.app.LibraryBoot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
class BookServiceTest {
    // anche in questo caso unit = component (come punto di partenza)
    @Autowired
    private BookService bookService;
    // in realtà se non creiamo l'implementazione farlocca di BookMapper
    // la nostra unit diventa BookServiceImpl + BookMapperImpl

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
        Long inputId = 9999L;
        Optional<BookResource> output = this.bookService.readBookById(inputId);
        assertFalse(output.isPresent());
    }

    @Test
    void createBookTest() {
        // non ho la possibilità (al momento) di testare l'autore corretto
        // potrei accontentarmi di testare che la property author dell'output
        // non sia null
        BookCreateDto inputDto = new BookCreateDto();
        inputDto.setTitle("Harry Potter e il secondo volume");
        inputDto.setAuthorId(2L);
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
        //può essere InvalidDataAccessApiUsageException se commento alcune righe di BookMapper
        assertThrows(NullPointerException.class, this::createBookNoAuthorImpl);
    }
}
