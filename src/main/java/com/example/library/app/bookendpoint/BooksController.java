package com.example.library.app.bookendpoint;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    // il controller dichiara le dipendenze solo verso gli orchestrator
    private final GetBookSingleOrchestrator getBookSingleOrchestrator;
    private final PostBookCreateOrchestrator postBookCreateOrchestrator;

    @GetMapping("/{bookId}")
    //altro esempio di securizzazione: voflio che solo determinate utenze possano accedere a un id
    //Per esempio solo maria può accedere all'id 1L
    //Devo poter accedere alle info dell'utente autenticato; lo faccio aggiungendo un parametro
    //al metodo del Controller
    ResponseEntity<BookResource> getBookSingle(@PathVariable Long bookId, Authentication auth) {
        //Se voglio che comunque ADMIN e LIBRARIAN possono legere il libro di Maria, non posso usare l'annotation secured
        //altrimenti Maria che non è nè librarian nè admin può leggere il suo libro
        boolean librarian = false;
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_LIBRARIAN") || ga.getAuthority().equals("ROLE_ADMIN")) {
                librarian = true;
                break;
            }
        }

        //Se bookId è 1L e auth.getName non è maria, sollevo un'eccezione
        if(!librarian && bookId == 1L && !"maria".equals(auth.getName()))
            throw new AccessDeniedException("Non sei Maria");
        // Spring Web ha estratto tutti i valori dalla request http
        // adesso il metodo del controller può eseguire l'orchestrator
        Optional<BookResource> value = this.getBookSingleOrchestrator.getBookSingle(bookId);
        // ottenuto il risultato Java, il controller con l'aiuto di Spring Web
        // e della libreria Jackson trasforma il risultato Java in un
        // documento JSON e una response http
        return ResponseEntity.of(value);
    }

    @PostMapping
    //Sicurizzo l'endpoint limitando i rupli che possono accedere a questa API
    @Secured({"ROLE_ADMIN", "ROLE_LIBRARIAN"})
    public ResponseEntity<?> postBookCreate(@RequestBody BookCreateDto dto){
        BookResource resource = this.postBookCreateOrchestrator.postBookCreate(dto);
        Long bookId = resource.getId();
        //aggiungere un parametri al metodo del controller causa problemi qui
        URI uri= fromMethodCall(on(this.getClass()).getBookSingle(bookId, null))
            .build()
            .toUri();
        return ResponseEntity.created(uri).build();
    }
}
