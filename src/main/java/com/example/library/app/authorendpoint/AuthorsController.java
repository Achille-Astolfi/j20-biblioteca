package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorsController {
    private final GetAuthorSingleOrchestrator getAuthorSingleOrchestrator;
    private final PostAuthorCreateOrchestrator postAuthorCreateOrchestrator;

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResource> getAuthorSingle(@PathVariable Long authorId) {
        Optional<AuthorResource> value = this.getAuthorSingleOrchestrator.getAuthorSingle(authorId);
        return ResponseEntity.of(value);
    }

    // Per creare un nuovo autore le "specifiche" (standard di fatto - vedi tesina di Roy Fielding)
    // dicono: fare POST sulla URI della collection, passando come body un documento JSON
    // contenente le informazioni necessarie per creare la nuova risorsa
    // NOTA BENE: come per tutte le POST sulla URI della collection, l'id di sicuro
    // è un dato che NON va specificato in quanto viene assegnato "a posteriori"
    // Possiamo partire dal modello del JSON, quindi per iniziare andiamo nel package model.author
    // Che cosa restituisco? Compromesso. Le "specifiche" dicono
    // 1) CREATED (senza body) se la creazione è SINCRONA
    // 2) ACCEPTED (senza body) se invece la creazione è ASINCRONA
    // Nella realtà, spesso viene chiesto dal frontend una risposta diversa
    // per esempio 200 OK con body = alla nuova risorsa creata
    @PostMapping // POST secca sulla collection
    // L'annotation RequestBody significa che mi aspetto un body in request il cui modello
    // è il tipo del parametro annotato
    public ResponseEntity<?> postAuthorCreate(@RequestBody AuthorCreateDto dto) {
        // siccome required=true è valore di default, sono sicuro che nel giro corretto
        // dto non è null, quindi non ho controlli da fare
        AuthorResource resource = this.postAuthorCreateOrchestrator.postAuthorCreate(dto);
        // SE il front-end mi chiede di rispondere 200, ecco la risposta:
        // of se ho un optional, ok se ho l'oggetto "sicuramente" non-null
//        return ResponseEntity.ok(resource);
        Long authorId = resource.getId();
        // Ho un'API di Spring che mi permette di costruire la URI completa di una GET
        // partendo dall'invocazione "finta" del metodo
        URI uri = fromMethodCall(on(this.getClass()).getAuthorSingle(authorId)).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
