package com.example.library.app.authorendpoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.library.model.author.AuthorResource;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AuthorsControllerTest {

  @Test
  void getAuthorSingleNotFoundTest() throws Exception {
    // istruisco Mockito (il MockBean) a rispondere con un Optional vuoto
    // se richiedo id == 0L
    Long input = 0L;
    Optional<AuthorResource> output = Optional.empty();
    // in alternativa a input si può usare eq(input)
    when(this.authorService.readAuthorById(eq(input)))
        .thenReturn(output);

    // invoco /authors/0 e mi aspetto una risposta 404
    this.mockMvc.perform(get("/authors/0"))
        .andExpect(status().isNotFound());
  }
}
