package com.example.library.app.bookendpoint;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    // il controller dichiara le dipendenze solo verso gli orchestrator
    private final GetBookSingleOrchestrator getBookSingleOrchestrator;
    private final PostBookCreateOrchestrator postBookCreateOrchestrator;

    @GetMapping("/{bookId}")
    // altro modo di securizzare è: solo determinate utenze possono accedere a un id
    // per esempio Achille può vedere solo il suo saldo, Elisa può vedere solo il suo saldo
    // eccetera
    // Posso simulare la cosa dicendo: solo l'utente con nome "maria" può accedere all'id 1L
    // Devo poter accedere alle informazioni dell'utente autenticato; lo faccio
    // aggiungendo un parametro al metodo del Controller
    ResponseEntity<BookResource> getBookSingle(@PathVariable Long bookId, Authentication auth) {
        // se voglio che comunque LIBRARIAN e ADMIN possano leggere il libro di maria
        // NON posso usare l'annotation @Secured perché altrimenti maria (che non è LIBRARIAN
        // e non è ADMIN) non potrebbe leggere il proprio libro
        boolean librarian = false;
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_LIBRARIAN") || ga.getAuthority().equals("ROLE_ADMIN")) {
                librarian = true;
                break;
            }
        }
        // se bookId == 1 e auth.getName() non è "maria" sollevo un'eccezione
        if (!librarian && bookId == 1L && !"maria".equals(auth.getName())) {
            throw new AccessDeniedException("Non sei maria");
        }
        // Spring Web ha estratto tutti i valori dalla request http
        // adesso il metodo del controller può eseguire l'orchestrator
        Optional<BookResource> value = this.getBookSingleOrchestrator.getBookSingle(bookId);
        // ottenuto il risultato Java, il controller con l'aiuto di Spring Web
        // e della libreria Jackson trasforma il risultato Java in un
        // documento JSON e una response http
        return ResponseEntity.of(value);
    }

    @PostMapping
    // securizzo l'endpoint limitando i ruoli che possono accedere
    // i ruoli sono in "or"
    @Secured({"ROLE_ADMIN", "ROLE_LIBRARIAN"})
    ResponseEntity<?> postBookCreate(@RequestBody BookCreateDto dto) {
        BookResource resource = this.postBookCreateOrchestrator.postBookCreate(dto);
        Long bookId = resource.getId();
        // aggiungere un parametro al metodo del controller causa problemi qui sotto
        URI location = fromMethodCall(on(getClass()).getBookSingle(bookId, null)).build().toUri();
        return ResponseEntity.created(location).build();
    }
}
