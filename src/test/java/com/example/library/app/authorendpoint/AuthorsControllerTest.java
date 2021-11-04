package com.example.library.app.authorendpoint;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.hamcrest.core.StringEndsWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
// in più quando si testa un controller si aggiunge
@AutoConfigureMockMvc

class AuthorsControllerTest {
  // inoltre si aggiunge in Autowired un component della class MockMvc
  @Autowired
  private MockMvc mockMvc;
  // per nostra convenzione, la unit è composta dal Controller iù gli orchestrator
  // non c'è bisogno di mettere in autowired il component AuthorsControllers
  //  è sufficiente MockMvc (simula la chiamata all'endpoint)

  // vedi descrizione sotto:ho bisogno di dichiarare un'implementazione farlocca di AuthorService
  // l'annotation per indicare che l'implementazione è ad hoc:
  @MockBean
  private AuthorService authorService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser("user")
  void getAuthorSingleTest() throws Exception{
    // test positivo di getAuthorsSingle: chiamata GET a /authors/1
    // NB i service non fanno parte della Unit che sto testandi
    // altrimenti sarebbe integration test, quindiin questo caso non dobbiamo
    // creare record del "database di test" ma simulare il comortamente atteso del/dei service

    // Prima di invocare mockMvc dobbiamo specificare a SpringBoot il Body del motodo readAuthorById

    // INPUT del motodo è Long 1L
    Long input = 1L;
    // OUTPUT del metodo è un Optional che contiene nAuthor REsource con id == input e lastname a piacere
    AuthorResource resource = new AuthorResource();
    resource.setId(input);
    resource.setLastName("Grazia Deledda");
    Optional<AuthorResource> output = Optional.of(resource);
    // specifico il "body" con la sintassi di MockIto
    when(this.authorService.readAuthorById(input))
        .thenReturn(output);

    // INPUT: come input non abbiamo niente, è la URI stessa
    // OUTPUT atteso: un JSON modellato da AuthorRecource
    // dentro a perfom posso mettere anche tutti gli header chemi servono, se mi servono
    // altrimenti usa quelli di default: accept: application/json e content-type: application/json
    // potrebbe essere anche */* in entrambi i casi, poco importa

    this.mockMvc.perform(get("/authors/1"))
        // per convenzione si va a capo
        .andExpect(status().isOk())
        // mi aspetto anche che la resource restituisca abbia come valore la chiave id
        // il valore 1
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.lastName").exists());
  }
  @Test
  @WithMockUser("user")
  void getAuthorSingleNotFoundTest() throws Exception{
      // invoco /authors/0 e mi aspeto una risposta 404
      // istruisco mockito(il mockbean) a rispondere con un optional vuoto se richiedo id == 0L
      Long input = 0L;
      Optional<AuthorResource> output = Optional.empty();
      // in alternativa si potrebbe scrivere eq(input)
      when(this.authorService.readAuthorById(input))
          .thenReturn(output);

      this.mockMvc.perform(get("/authors/0"))
          .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser("user")
  void postAuthorCreateTest() throws Exception{
      // istruisco il MockBean
    AuthorCreateDto input = new AuthorCreateDto();
    input.setLastName("Fo");
    input.setFirstName("Dario");
    // NB è vero che possiamo scrivere quelloc he vogliamo, ma vgoliamo emualre il comportamento corretto
    // quindi scriviamo i dati in modo coerente tra input e output
    AuthorResource output = new AuthorResource();
    output.setLastName("Fo");
    output.setFirstName("Dario");
    output.setId(1L);
    // questa parte è sempre la stessa
    when(this.authorService.createAuthor(input))
        .thenReturn(output);
    // la novità questa volta è la presenza di un body nella request
    // il modello del json è forza per caso forse no, un oggetto AuthorCreateDto e fatalità è la nostra variabile
    // che si chiama input
    // come faccio a trasformare il mio modello java in un documento JSON in una stringa?
    // si usa la libreria Jackson
    String jsonInput = this.objectMapper.writeValueAsString(input);
    // AuthorCreateDto alContrario = this.objectMapper.readValue(jsonInput, AuthorCreateDto.class); esempio
    this.mockMvc.perform(post("/authors/").content(jsonInput).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(header().string("Location", new StringEndsWith("/authors/1")));
  }

  @Test
  @WithMockUser("user")
  void postAuthorCreateNoNameTest() throws Exception{
    //input DTO senza ne nome ne cognome
    assertThrows(NestedServletException.class, this::postAuthorCreateNoNameImpl);

  }

  private void postAuthorCreateNoNameImpl() throws Exception{
    AuthorCreateDto input = new AuthorCreateDto();

    when(this.authorService.createAuthor(input))
        .thenThrow(DataIntegrityViolationException.class);

    String jsonInput = this.objectMapper.writeValueAsString(input);  //in alternativa jsonInput = {}

    this.mockMvc.perform(post("/authors/").content(jsonInput).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

}
