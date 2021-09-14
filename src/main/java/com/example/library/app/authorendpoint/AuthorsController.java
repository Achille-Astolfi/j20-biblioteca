package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.book.BookResource;
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
public class AuthorsController {
  private final GetAuthorSingleOrchestrator getAuthorSingleOrchestrator;

  @GetMapping("/{authorId}")
  public ResponseEntity<AuthorResource> getAuthorSingle(@PathVariable Long authorId){
    Optional<AuthorResource> value = this.getAuthorSingleOrchestrator.getAuthorSingle(authorId);
    return ResponseEntity.of(value);
  }

  //per creare un nuovo autore le "specifiche" (standard di fatto - vedi tesina di Roy Fielding)
  //dicono: fare POST sulla URI della collection, passando come body un documento JSON
  //contenente le informazioni necessarie per creare la nuova risorsa
  //NOTA BENE: come per tutte le POST sulla URI della collection, l'id di sicuro
  //è un dato che non va specificato in quanto viene assegnato "a posteriori"
  //Possiamo partire dal modello del JSON, quindi per iniziare andiamo nel
  //package model.author e creiamo AuthorsCreateDTO
  //Cosa restituisco? Compromesso. Le "specifiche" dicono
  //1) CREATED (senza body) se la creazione è sincrona
  //2) ACCEPTED (senza body) se la creazione è asincrona: lo prendo in carico ma non è detto che lo crei subito
  //Nella realtà spesso viene chiesto da frontend una risposta diversa, per esempio
  //200 OK con body = nuova risorsa creata
  @PostMapping//POST secca sulla collection
  //l'annotation RequestBody significa che mi aspetto un body in request il cui modello
  //è il tipo del parametro annotato
  public ResponseEntity<?> postAuthorCreate(@RequestBody AuthorCreateDTO dto){
    return ResponseEntity.noContent().build();
  }
}
