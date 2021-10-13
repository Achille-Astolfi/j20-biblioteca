package com.example.library.app.authorendpoint;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringEndsWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
// in più, quando si testa un controller si aggiunge:
@AutoConfigureMockMvc
class AuthorsControllerTest {
    // e inoltre, si aggiunge in Autowired un component della class MockMvc
    @Autowired
    private MockMvc mockMvc;

    // per nostra convenzione, la unit è composta dal Controller più gli Orchestrator
    // non c'è bisogno di mettere in autowired il component AuthorsController
    // è sufficiente MockMvc

    // vedi descrizione sotto: ho bisogno di dichiarare un'implementazione ad hoc di AuthorService
    // l'annotation per indicare che l'implementazione è ad hoc:
    @MockBean
    private AuthorService authorService;

    // per trasformare i modelli Java in documenti JSON (String) uso la libreria Jackson che è un component Spring
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAuthorSingleTest() throws Exception {
        // test positivo di getAuthorSingle: chiamata GET a /authors/1
        // NOTA BENE: i Service NON fanno parte della Unit che sto testando
        // (altrimenti sarebbe un integration test) Quindi in questo caso non dobbiamo
        // creare record nel "database di test" ma simulare il comportamento atteso del/dei Service

        // PRIMA di invocare mockMvc dobbiamo specificare a SpringBoot il "body" del metodo readAuthorById
        // INPUT del metodo è Long 1L
        Long input = 1L;
        // OUTPUT del metodo è un Optional che contiene un AuthorResource con id == input e lastName a piacere
        AuthorResource resource = new AuthorResource();
        resource.setId(input);
        resource.setLastName("Grazia Deledda");
        Optional<AuthorResource> output = Optional.of(resource);
        // Specifico il "body" con la sintassi di Mockito site.mockito.org
        when(this.authorService.readAuthorById(input))
                .thenReturn(output);

        // INPUT: come input non abbiamo niente, è la URI stessa
        // OUTPUT ATTESO: un JSON modellato da AuthorResource
        // dentro a perform posso mettere anche tutti gli header che mi servono, se mi servono
        // altrimenti usa quelli di default: accept: application/json e content-type: application/json
        // Potrebbe essere anche */* in entrambi i casi, poco importa
        // Come la farebbe Postman
        this.mockMvc.perform(get("/authors/1"))
                // per convenzione si va a capo
                .andExpect(status().isOk())
                // mi aspetto anche che la resource restituita abbia come valore per la chiave id
                // il valore 1
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").exists());
    }

    @Test
    void getAuthorSingleNotFoundTest() throws Exception {
        // istruisco Mockito (il MockBean) a rispondere con un Optional vuoto se richiedo id == 0L
        Long input = 0L;
        Optional<AuthorResource> output = Optional.empty();
        // in alternativa a input si può usare eq(input) - semanticamente più corretto, il risultato è lo stesso
        // nel caso in cui il metodo da emulare avesse più di un parametro, nella maggior parte dei casi funziona
        // meglio con eq(...) piuttosto che senza
        when(this.authorService.readAuthorById(eq(input)))
                .thenReturn(output);

        // invoco /authors/0 e mi aspetto una risposta 404
        this.mockMvc.perform(get("/authors/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postAuthorCreateTest() throws Exception {
        // istruisco il MockBean
        AuthorCreateDto input = new AuthorCreateDto();
        input.setLastName("Fo");
        input.setFirstName("Dario");
        // NOTA BENE: è vero che possiamo scrivere quello che vogliamo, ma vogliamo emulare il comportamento corretto
        // quindi scriviamo i dati in modo coerente tra input e output
        AuthorResource output = new AuthorResource();
        output.setLastName("Fo");
        output.setFirstName("Dario");
        output.setId(1L);
        // questa parte qui è sempre la stessa
        when(this.authorService.createAuthor(input))
                .thenReturn(output);

        // la novità questa volta è la presenza di un body nella Request
        // il modello del JSON è (forse per caso forse no) un oggetto AuthorCreateDto e fatalità è la nostra variabile
        // che si chiama input
        // Come faccio a trasformare il mio modello Java in un documento JSON (che è una string)?
        String jsonInput = this.objectMapper.writeValueAsString(input);
//        AuthorCreateDto alContrario = this.objectMapper.readValue(jsonInput, AuthorCreateDto.class);
        this.mockMvc.perform(post("/authors").content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", new StringEndsWith("/authors/1")));
    }

    @Test
    void postAuthorCreateNoNameTest() throws Exception {
        // il test si comporta in modo diverso dall'esercizio
        assertThrows(NestedServletException.class, this::postAuthorCreateNoNameImpl);
    }

    private void postAuthorCreateNoNameImpl() throws Exception {
        // input DTO senza né nome né cognome
        // istruisco il MockBean
        AuthorCreateDto input = new AuthorCreateDto();
        when(this.authorService.createAuthor(input))
                .thenThrow(DataIntegrityViolationException.class);

        String jsonInput = this.objectMapper.writeValueAsString(input); // in alternativa jsonInput = "{}";
        this.mockMvc.perform(post("/authors").content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}