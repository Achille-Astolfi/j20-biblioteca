package com.example.library.app.bookendpoint;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorResource;
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
  @MockBean
  private BookService bookService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser("user")
  void getBookSingleTest() throws Exception{
    // INPUT
    Long input = 1L;
    // OUTPUT
    BookResource resource = new BookResource();
    resource.setId(input);
    resource.setTitle("Il dio del fiume");
    resource.setAuthor("Wilbur Smith");
    Optional<BookResource> output = Optional.of(resource);
    // istruisco il MockBean bookservice
    when(bookService.readBookById(eq(input)))
        .thenReturn(output);
    // eseguo chiamata
    this.mockMvc.perform(get("/books/1", input))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(input))
        .andExpect(jsonPath("$.title").exists());

  }

  @Test
  @WithMockUser(authorities = "ROLE_LIBRARIAN")
  void postBookCreateTest() throws Exception{
    // INPUT
    BookCreateDto input = new BookCreateDto();
    input.setAuthorId(1L);
    String titolo = "titolo";
    input.setTitle(titolo);
    //OUTPUT
    BookResource output = new BookResource();
    output.setId(2L);
    output.setTitle(titolo);
    output.setAuthor("Autore");
    // istruisco il book service
    when(this.bookService.createBook(eq(input)))
        .thenReturn(output);
    //eseguo la chiamata mockMVC
    String jsonRequest = this.objectMapper.writeValueAsString(input);
    this.mockMvc.perform(post("/books/")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", new StringEndsWith("/books/2")));
  }

}
