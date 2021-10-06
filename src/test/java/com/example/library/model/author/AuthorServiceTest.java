package com.example.library.model.author;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
class AuthorServiceTest {
  // intreccio Jupiter con spring framework in due modi
  // 1. dichiarando la dipendenza del component che è la mia unit
  // 2. con una annotation sulla class
  @Autowired
  private AuthorService authorService;
  @Test
  void readAuthorByIdTest() {
    Long inputId = 1L;
    Optional<AuthorResource> output = this.authorService.readAuthorById((inputId));
    assertTrue(output.isPresent());
    AuthorResource resource = output.get();
    assertEquals(inputId, resource.getId());
  }

  @Test
  void readAuthorByIdNotExistentTest() {
    Long inputId = 0L;
    Optional<AuthorResource> output = this.authorService.readAuthorById((inputId));
    assertFalse(output.isPresent());
  }

  @Test
  void readAuthorByIdNullArgumentTest() {
  }

  @Test
  void createAuthorTest() {
    AuthorCreateDto inputDto = new AuthorCreateDto();
    inputDto.setFirstName("Grazia");
    inputDto.setLastName("Deledda");
    AuthorResource output = this.authorService.createAuthor(inputDto);
    //risultato not null
    assertNotNull(output);
    //mi aspetto che il nome e il cognome della resource siano uguali al nome e cognome del dto
    assertEquals(inputDto.getFirstName(), output.getFirstName());
    assertEquals(inputDto.getLastName(), output.getLastName());
  }

  @Test
  void createAuthornoNameTest() {
  }
}
