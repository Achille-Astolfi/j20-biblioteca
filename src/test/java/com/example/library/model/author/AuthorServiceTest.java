package com.example.library.model.author;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;

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
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/author.sql")
class AuthorServiceTest {
  // intreccio Jupiter con spring framework in due modi
  // 1. dichiarando la dipendenza del component che è la mia unit
  // 2. con una annotation sulla class
  @Autowired
  private AuthorService authorService;
  @Test
  @Order(1)
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
    // l'obbietivo del test è evocare readAuthorById passando l'argomento null
    // e verificare che venga sollevata l'eccezione attesa
    // si tratta di un'eccezione di Spring Data,
    // perchè tutte le ecceioni sollevate dalle class annotate con @Repository
    // vengono incapsulate in queste eccezioni di Spring data
    // Il metodo per intercettare le ecceizoni attese è aassertThrows che vuole
    // come primo argomento la class dell'eccezione da verificare
    // e come secondo argomento vuole una "lambda" o un "method referece"
    // Scriviamo un metodo private dentro questa class di test con il test
    // da eseguire e che vogliamo solevi l'eccezione
    // Ho bisogno di verficare che l'eccezione sollevata abbia come cause illegalArgmentException
    // devo invocare readAuthorByIdNullArgumentImpl() non direttamente ma come method reference
    // Interpreto il method refrence non come invocazione istantanea ma come invocazione differita

    RuntimeException exception = assertThrows(RuntimeException.class, this::readAuthorByIdNullArgumentImpl);
    // i più pignoli possono mettere un'assert che getcause() sia notNull
    assertNotNull(exception.getCause());
    // verifico che la cause sa il tipo IllegalArgumentException
    assertEquals(IllegalArgumentException.class, exception.getCause().getClass());


  }
  private void readAuthorByIdNullArgumentImpl() {
    // questo è il test che voglio eseguire nel metodo readAuthorByIdNullArgumentTest()
    // e che voglio sollevi una eccezione
    this.authorService.readAuthorById(null);
  }

  @Test
  @Order(0)
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
  void createAuthorNoNameTest() {
    assertThrows(DataIntegrityViolationException.class, this::createAuthorNoNameImpl);
  }
  private void createAuthorNoNameImpl() {
    // crea un DTO ma non imposto ne nome ne cognome
    AuthorCreateDto dto = new AuthorCreateDto();
    this.authorService.createAuthor(dto);
  }

  @Test
  void createAuthorNullArgumentTest() {
    // come sopra readAuthorByIdNullArgumenttest
    InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, this::createAuthorNullArgumentImpl);
    assertNotNull(exception.getCause());
    assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
  }

  private void createAuthorNullArgumentImpl() {
    this.authorService.createAuthor(null);
  }
}
