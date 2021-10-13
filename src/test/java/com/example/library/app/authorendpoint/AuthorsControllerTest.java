package com.example.library.app.authorendpoint;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorCreateDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
//si aggiunge per testare un controller
@AutoConfigureMockMvc
class AuthorsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  //ho bisogno di un'implementazione ad hoc di AuthorService
  //l'annotation per indicare che l'implementazione è ad hoc:
  @MockBean
  private AuthorService authorService;

  //per trasferire o modelli Java in socumenti JSON usa la libreria Jackson che è un componet
  //Spring
  @Autowired
  private ObjectMapper mapper;

  //per nostra convenzione, la unit è composta dal Controller più gli Orchestrator
  //non c'è bisogno di mettere un autowired il component AuthorsController
  //è sufficiente MockMvc

  @Test
  void getAuthorSingleTest() throws Exception {
    //test positivo di getAuthorSingle: chiamata GET a /authors/1
    //NB: i service non fanno parte della Unit che stiamo testando
    //(altrimenti sarebbe un'integration test) quindi in questo caso
    //non dobbiamo creare un record nel db di test ma
    //simulare il comportamento del/dei service

    //PRIMA di invocare mockMvc dobbiamo dobbiamo specificare a SpringBoot il "body"
    //del metodo readAuthorById
    //Input del metodo è Long 1L
    Long input = 1L;
    AuthorResource resource = new AuthorResource();
    resource.setId(input);
    resource.setLastName("Deledda");
    resource.setFirstName("Grazia");
    Optional<AuthorResource> output = Optional.of(resource);
    //Specifico il body con la sintassi di Mockito
    when(this.authorService.readAuthorById(input))
        .thenReturn(output);

    //L'input è la URI stessa
    //L'output è un documento JSON modellato da AuthorResource

    //dentro a perform posso mettere anche tutti fli header che mi servono
    //altrimenti usa quello di default: accept: application/json e content-type: application/json
    //Potrebbe essere anche */* in entrambi i casi, poco importa
    //Come farebbe Postman
    this.mockMvc.perform(get("/authors/1"))
        .andExpect(status().isOk())
        //mi aspetto anche che la resource restituita abbia 1 come chiave id
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.lastName").exists());
  }

  @Test
  void getAuthorSingleNotFoundTest() throws Exception {
    //invoco /authors/0 e mi aspetto 404
    //istruisco mockito a rispondere con un Optional vuoto se chiedo id 0
    when(this.authorService.readAuthorById(0L))
        .thenReturn(Optional.empty());
    this.mockMvc.perform(get("/authors/0"))
        .andExpect(status().isNotFound());
  }

  @Test
  void postAuthorCreateTest() throws Exception {
    //istruisco il MockBean
    AuthorCreateDTO input = new AuthorCreateDTO();
    input.setLastName("Fo");
    input.setFirstName("Dario");
    AuthorResource output = new AuthorResource();
    output.setLastName("Fo");
    output.setFirstName("Dario");
    output.setId(1L);

    when(this.authorService.createAuthor(input))
        .thenReturn(output);
    //la novità è la presenza di un body nella request
    //il modello del JSON è un oggetto AthorCreeateDTO  che è la nostra variabile input
    String jsonInput = this.mapper.writeValueAsString(input);
    //AuthorCreateDTO alContrario = this.mapper.readValue(jsonInput, AuthorCreateDTO.class);
    this.mockMvc.perform(post("/authors").content(jsonInput)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", new StringEndsWith("/authors/1")));
  }

  @Test
  void postAuthorCreateNoNameTest() throws Exception {
    assertThrows(NestedServletException.class, this::postAuthorCreateNoNameImpl);
  }

  private void postAuthorCreateNoNameImpl() throws Exception {
    AuthorCreateDTO input = new AuthorCreateDTO();
    when(this.authorService.createAuthor(input))
        .thenThrow(DataIntegrityViolationException.class);

    String jsonInput = this.mapper.writeValueAsString(input);
    this.mockMvc.perform(post("/authors")
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());

  }

}
