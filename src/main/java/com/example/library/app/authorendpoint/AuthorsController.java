package com.example.library.app.authorendpoint;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;

import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public abstract class AuthorsController {

  private final GetAuthorSingleOrchestrator getAuthorSingleOrchestrator;
  private final PostAuthorCreateOrchestrator postAuthorCreateOrcherstrator;

  @GetMapping("/{authorId}")
  ResponseEntity<AuthorResource> getAuthorSingle(@PathVariable Long authorId) {
    Optional<AuthorResource> value = this.getAuthorSingleOrchestrator.getAuthorSingle(authorId);
    return ResponseEntity.of(value);
  }

  // per creare un nuovo autore le "specifiche" (standard di fatto)
  // dicono: fare POST sulla URI della collection, pasando come body un documento JSON
  // contenente informazioni necessarie per creare la nuova risorsa
  // necessarie perchè sono quelle che vengono passate dal frontend
  // per tutte le POST sulla URI della collezione l'id di sicuro è
  // un dato che non va specificato in quanto viene assegnato a posteriori
  // possiamo partire dalla modello del JSON.
  @PostMapping //POST secco sulla collector
  // l'annotation Request Body signfica che mi aspetto un body in request
  // il cui modello è il tipo del parametro annotato
  public ResponseEntity<?> postAuthorCreate(@RequestBody AuthorCreateDto dto) {
    AuthorResource resource =  this.postAuthorCreateOrcherstrator.postAuthorCreate(dto);
    // return ResponseEntity.ok(resource);
    Long authorId = resource.getId();
    // esiste una API di Spring che permette di costruire una URI di una GET
    // partendo dall'invocazione Finta del metodo
    URI uri = fromMethodCall(on(this.getClass()).getAuthorSingle(authorId)).build().toUri();
    return ResponseEntity.created(uri).build();
  }
}


