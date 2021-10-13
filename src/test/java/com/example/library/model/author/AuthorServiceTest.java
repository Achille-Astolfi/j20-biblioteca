package com.example.library.model.author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.library.app.LibraryBoot;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    //se input è null mi aspetto che sia sollevata IllegalArgumentException


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

  }

}
