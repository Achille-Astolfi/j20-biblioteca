package com.example.library.app.bookendpoint;

import com.example.library.app.LibraryBoot;
import com.example.library.app.book.Book;
import com.example.library.model.author.AuthorService;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringEndsWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@AutoConfigureMockMvc
class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser("maria")
    void getBookSingleTest() throws Exception {
        // definisco input
        Long input = 1L;
        // definisco output
        BookResource resource = new BookResource();
        resource.setId(input);
        resource.setTitle("Puerto Escondido");
        resource.setAuthor("Pino Cacucci");
        Optional<BookResource> output = Optional.of(resource);
        // istruisco il MockBean bookService
        when(this.bookService.readBookById(eq(input)))
                .thenReturn(output);
        // eseguo la chiamata mockMvc
        this.mockMvc.perform(get("/books/{id}", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(input))
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void postBookCreateTest() throws Exception {
        String title = "La circonferenza di una nuvola";
        // definisco input
        BookCreateDto input = new BookCreateDto();
        input.setAuthorId(1L);
        input.setTitle(title);
        // definisco output
        BookResource output = new BookResource();
        output.setId(2L);
        output.setTitle(title);
        output.setAuthor("Carolina Capria");
        // istruisco il MockBean bookService
        when(this.bookService.createBook(eq(input)))
                .thenReturn(output);
        // eseguo la chiamata mockMvc
        String jsonRequest = this.objectMapper.writeValueAsString(input);
        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", new StringEndsWith("/books/2")));
    }
}
