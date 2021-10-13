package com.example.library.model.author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.library.app.LibraryBoot;
import java.util.Optional;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

//Per convenzione la class per testare la unit Pippo si chiama PippoTest
//La class può essere public ma è consigliato che non lo sia
//Ho un metodo per ogni operazione che voglio testare; in realtà non va bene,
//andrebbero creati due metosi per ogni operzione.
//Ogni metodo può essere public, non deve essere private, è consigliato che sia
//"package protectes" ossia senza modificatore della visibilità
//Deve essere annotato con @Test e deve essere void

//Non siamo sotto l'ombrello app, dobbiamo specificare tra gli attributi dell'annotation
//qual è la class annotata con SpringBootApplication, semplicemente perché è la miglior candidata
//per dichiarare qual è l'ombrello
@SpringBootTest(classes = LibraryBoot.class)
//IL COMPONENT AUTHORSERVICEIMPL HA DUE DIPENDENZE.
//*Quella verso i repository creati ad hoc per il test si realizza dichiarando con una annotation
//di Spring Framework un DB in-memory (di solito h2) ce viene creato e ripulito automaticamente
//dalla sinergia Jupter + Spring Boot + Spring Framework
//L'annotation non è sufficiente, ma è necessaria
//il valore associato all'annotation fa leggere a Spring Boot anche il file
//application-jupiter.yml che di fatto sovrascrive il file application.yml
//*Quella verso Mapstruct avrebbe bisogno di un componente ad hoc per authormapper, ma non lo facciamo
//Quindi la unit non è composta solo dal componente AuthorServiceImpl, ma
//dalla catena di componenti AuthorServiceImpl + AuthorMapperImpl
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorServiceTest {
  //intreccio Jupiter con Spring Framework in due modi:
  //1) dichiariamo la dipendenza dal component che è la mia unit
  //2) con una annotation sulla class
  @Autowired
  //Qui va authowired non final con @RequiredArgsConstructor
  private AuthorService authorService;

  //Per convenzione i test positivi li chiamiamo col nome del metodo più test
  //quelli negativi, il nome del metodo, più il motivo per cui il test è negativo, più test

  //I test negativi hanno senso nel bilancio dei costi/benefici
  //Cosa rischio se un particolare metodo va storto?
  @Test
  @Order(1)
  void readAuthorByIdTest() {
    //a fronte di un input con argomento 1L o 2L (i dati che ho inserito a db)
    //invoco il metodo readAuthorById
    //mi aspetto che l'output sua un Optional pieno
    //il cui contenuto è un AuthorResource con id uguale al valore di input
    Long inputId = 1L;
    Optional<AuthorResource> output = this.authorService.readAuthorById(inputId);
    assertTrue(output.isPresent());
    AuthorResource resource = output.get();
    assertEquals(inputId, resource.getId());
  }

  @Test
  void readAuthorByIdNotExistingTest() {
    //a fronte di un input con argomento 3L (diverso da 1 o 2)
    //invoco il metodo readAuthorById
    //mi aspetto che l'output sua un Optional vuoto
    Long inputId = 999L;
    Optional<AuthorResource> output = this.authorService.readAuthorById(inputId);
    //assertTrue(output.isEmpty());
    //meglio questo perché il metodo isEmpty() in java.util.Optional non esiste in Java 8
    assertFalse(output.isPresent());
  }

  @Test
  void readAuthorByIdNullArgumentTest() {
    //se input è null mi aspetto che invocando readAuthorById
    // sia sollevata un'eccezione di Spring Data perché tutte le eccezioni
    //sollevate dalle class annotate con @Repository vengono incapsulate
    //in eccezioni di Spring Data
    //Il metodo per intercettare le eccezioni aattese è assertThrows che vuole
    //come primo argomento la class dell'eccezione da verificare e come secondo
    // argomento vuole una "lambda" o un "method reference"
    //Per semplificare la vita, scriviamo un metodo private dentro questa class di test
    //con il test da eseguire e che vogliamo sollevi l'eccezione
    //Ho bisogno di verificare che l'eccezione sollevata abbia come cause IllegalArgumentException
    //Devo invocare readAuthorByIdNullArgumentImpl non direttamente ma come method reference
    //Interpreto il method reference non come una invocazione istantanea ma come invocazione differita
    RuntimeException exception = assertThrows(RuntimeException.class, this::readAuthorByIdNullArgumentImpl);
    //i più pignoli possono mettere un'assert che getCause sia non null
    assertNotNull(exception.getCause());
    //verifica che la cause sia di tipo IllegalArgumentException
    assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
  }

  //tolgo la parola "Test" perché Sonar non ama molto i medodi che contengono questa parola
  private void readAuthorByIdNullArgumentImpl() {
    //questo è il test che voglio eseguire nel metodo readAuthorByIdNUllArgumentTest()
    //e che voglio che sollevi un'eccezione
    this.authorService.readAuthorById(null);
  }

  @Test
  @Order(0)
  void createAuthorTest() {
    //leggo sulla Use Case la normale operatività
    //a fronte della richiesta di inserimento di un nuovo autore
    //fornendo nome e cognome mi aspetto di creare una nuova resource
    //come da richiesta del "client".
    AuthorCreateDTO input = new AuthorCreateDTO();
    input.setFirstName("Grazia");
    input.setLastName("Deledda");
    AuthorResource output = this.authorService.createAuthor(input);
    //mi aspetto che l'output sia non nullo
    assertNotNull(output);
    assertEquals(input.getFirstName(), output.getFirstName());
    assertEquals(input.getLastName(), output.getLastName());
    //il set è fallito il problema era nella classe Author di java, la soluzione è aggiungere lìannotation
    //@Setter alla clss e togliere @Builder
    //TODO: ricordarsi di togliere builder
  }

  @Test
  void createAuthorNoNameTest() {
    //mi aspetto una exception di Spring Data
    assertThrows(DataIntegrityViolationException.class, this::createAuthorNoNameImpl);
    }

  private void createAuthorNoNameImpl() {
    AuthorCreateDTO dto = new AuthorCreateDTO();
    this.authorService.createAuthor(dto);
  }

  @Test
  void createAuthorNullArgumentTest() {
    //come sopra readAuthorByIdNullArgumentTest
   InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, this::createAuthorNullArgumentImpl);
   assertNotNull(exception.getCause());
   assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
  }

  private void createAuthorNullArgumentImpl() {
    this.authorService.createAuthor(null);
  }

}
