package com.example.library.model.author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.library.app.LibraryBoot;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/*
 * Per convenzione, la class per testare la unit Pippo si chiama PippoTest
 *
 * La class può essere public, ma è consigliato che non lo sia.
 *
 * Ho un metodo per ogni operazione che voglio testare; in realtà non va bene,
 * andrebbero creati due metodi per ogni operazione.
 *
 * Ogni metodo può essere public non deve essere private è consigliato che sia
 * "package protected" ossia senza modificatore della visibilità;
 * deve essere annotato con @Test e deve essere void
 * non deve avere parametri (a meno che non facciamo testing avanzato)
 */
// l'annotation è @SpringBootTest; occhio perché qui NON siamo sotto
// l'ombrello app, dobbiamo specificare tra gli attributi dell'annotation
// qual è la class annotata con @SpringBootApplication perché è la miglior
// candidata per dichiarare qual è "l'ombrello"
@SpringBootTest(classes = LibraryBoot.class)
// il component AuthorServiceImpl ha due dipendenze; la dipendenza verso i repository
// creati ad hoc per il test si realizza dichiarando con una annotation di Spring Framework
// un database in-memory (di solito un H2) che viene creato e ripulito automaticamente
// dalla sinergia Jupiter+Spring Boot +Spring Framework
// L'annotation non è sufficiente ma è necessaria
// Il valore associato all'annotation fa leggere a Spring Boot anche il file
// application-jupiter.yml che di fatto va a "sovrascrivere" alcuni valori di application.yml
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// aggiungo una annotation
@Sql("/author.sql")
class AuthorServiceTest {
    // intreccio Jupiter con Spring Framework in due modi
    // 1) dichiarando la dipendenza dal component che è la mia unit
    // 2) con una annotation sulla class
    @Autowired
    private AuthorService authorService;

    // andrebbe fatto un componente ad hoc anche per AuthorMapping, però non lo facciamo
    // quindi la nostra Unit non è composta solo dal componente AuthorServiceImpl ma dalla
    // catena di componenti AuthorServiceImpl + AuthorMapperImpl

    /*
     * Per convenzione, i test positivi io lo chiamo col nome del metodo più Test
     * (test positivo significa il test del flusso descritto nello Use Case come
     * normale operatività)
     */
    @Test
    @Order(1)
    void readAuthorByIdTest() {
        // a fronte di un input con argomento 1 (o 2 a mia scelta)
        // invoco il metodo readAuthorById
        // mi aspetto che l'output sia un Optional pieno
        // il cui contenuto è un AuthorResource con id uguale al valore di input
        Long inputId = 1L;
        Optional<AuthorResource> output = this.authorService.readAuthorById(inputId);
        // Uso i metodi static della class Assertions (vedi import static là sopra)
        assertTrue(output.isPresent());
        // superato il passaggio, posso fare get
        AuthorResource resource = output.get();
        // questa volta uso assertEquals; ricordarsi che il primo argomento è il risultato atteso
        assertEquals(inputId, resource.getId());
    }

    /*
     * Per convenzione, i test negativi io li chiamo con nome del metodo più
     * descrizione della "negatività" più Test
     * (test negativo significa il test del flusso descritto nello Use Case come
     * "estensione" o "caso particolare" o "eccezione" della normale operatività)
     */
    @Test
    void readAuthorByIdNotExistentTest() {
        // a fronte di un input con argomento 3 (o qualunque altro diverso da 1 o 2)
        // invoco il metodo readAuthorById
        // mi aspetto che l'output sia un Optional vuoto
        Long inputId = 0L; // l'esperienza insegna
        Optional<AuthorResource> output = this.authorService.readAuthorById(inputId);
        // supponiamo di aver fatto copia e incolla dal test positivo
        // faccio prima a scrivere assertTrue(output.isEmpty()) o assertFalse(output.isPresent())?
        // NOTA BENISSIMO: il metodo isEmpty() in java.util.Optional non c'è in Java 8!
        assertFalse(output.isPresent());
        // in Java 11 posso scrivere
        // assertTrue(output.isEmpty());
    }

    /*
     * Un ulteriore metodo per test negativi (questo in particolare) ha senso
     * sempre nel bilancio tra costi e benefici. Quanto mi costa scriverlo?
     * Qual è il rischio se il metodo non si comporta come previsto?
     */
    @Test
    void readAuthorByIdNullArgumentTest() {
        // l'obiettivo del test è invocare readAuthorById passando l'argomento null
        // e verificare che venga sollevata l'eccezione attesa
        // si tratta di una eccezione di Spring Data perché tutte le eccezione sollevate dalle class
        // annotate con @Repository vengono incapsulate in queste eccezioni di Spring Data
        // Il metodo per intercettare le eccezioni attese è assertThrows che vuole come primo argomento
        // la class dell'eccezione da verificare e come secondo argomento vuole una "lambda" o un "method reference"
        // Per semplificarci la vita, scriviamo un metodo private dentro questa class di test con il test da eseguire
        // e che vogliamo sollevi l'eccezione
        // Ho bisogno di verificare che l'eccezione sollevata abbia come cause IllegalArgumentException
        // devo invocare readAuthorByIdNullArgumentImpl() non direttamente ma come method reference
        // Interpreto il method reference non come invocazione istantanea ma come invocazione differita
        assertThrows(IllegalArgumentException.class, this::readAuthorByIdNullArgumentImpl);
    }

    // tolgo la parola "Test" perché Sonar non ama molto i metodi che contengono questa parola
    private void readAuthorByIdNullArgumentImpl() {
        // questo è il test che voglio eseguire nel metodo readAuthorByIdNullArgumentTest() e che voglio che
        // sollevi una eccezione
        this.authorService.readAuthorById(null);
    }

    @Test
    @Order(0)
    void createAuthorTest() {
        // leggo sullo Use Case la normale operatività
        // a fronte della richiesta di inserimento di un nuovo autore
        // fornendo nome e cognome mi aspetto di creare una nuova resource
        // come da richiesta del "client"; in esercizio il client è l'orchestrator
        // durante i test il "client" è proprio questo metodo qui
        AuthorCreateDto inputDto = new AuthorCreateDto();
        inputDto.setFirstName("Grazia");
        inputDto.setLastName("Deledda");
        AuthorResource output = this.authorService.createAuthor(inputDto);
        // mi aspetto che output sia non null
        assertNotNull(output);
        // e mi aspetto che il nome e il cognome dell'output siano uguali al nome e
        // cognome del dto
        assertEquals(inputDto.getFirstName(), output.getFirstName());
        assertEquals(inputDto.getLastName(), output.getLastName());
        // Il test è fallito; il problema era nella class Author.java
        // La soluzione è aggiungere @Setter alla class e togliere @Builder
        // TODO: devo ricordarmi di togliere @Builder
    }

    @Test
    void createAuthorNoNameTest() {
        // mi aspetto una "non so quale exception" di Spring Data
        assertThrows(DbActionExecutionException.class, this::createAuthorNoNameImpl);
    }

    private void createAuthorNoNameImpl() {
        // creo un DTO ma non imposto né firstName né lastName
        AuthorCreateDto dto = new AuthorCreateDto();
        this.authorService.createAuthor(dto);
    }

    @Test
    void createAuthorNullArgumentTest() {
        // come sopra readAuthorByIdNullArgumentTest
        assertThrows(IllegalArgumentException.class, this::createAuthorNullArgumentImpl);
    }

    private void createAuthorNullArgumentImpl() {
        this.authorService.createAuthor(null);
    }
}