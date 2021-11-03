package com.example.library.app.bookendpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.library.app.LibraryBoot;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
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

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@AutoConfigureMockMvc
class BooksControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  //Ogni annotation MockBean causa la creazione di un'istanza di springboot
  //quando ho più test con Mockbean posso scrivere un'abstract class di test
  //che le altre classi estendono in modo da creare ununica istanza di Spring
  @MockBean
  private BookService bookService;

  @Test
  @WithMockUser("maria")
  public void getBookSingleTest() throws Exception {
    Long input = 1L;
    BookResource resource = new BookResource();
    resource.setId(input);
    resource.setTitle("Puerto Escondido");
    resource.setAuthor("Pino Cacucci");
    Optional<BookResource> output = Optional.of(resource);
    when(this.bookService.readBookById(eq(input)))
        .thenReturn(output);

    this.mockMvc.perform(get("/books/{id}", input))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(input))
        .andExpect(jsonPath("$.title").exists());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void postBookCreateTest() throws  Exception{
    String title = "La circonferenza di una nuvola";
    BookCreateDto input = new BookCreateDto();
    input.setAuthorId(1L);
    input.setTitle(title);

    BookResource output = new BookResource();
    output.setId(2L);
    output.setTitle(title);
    output.setAuthor("Carolina Capria");

    when(this.bookService.createBook(eq(input)))
        .thenReturn(output);

    String jsonRequest = this.objectMapper.writeValueAsString(input);

    this.mockMvc.perform(post("/books")
        .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", new StringEndsWith("/books/2")));

  }
}
