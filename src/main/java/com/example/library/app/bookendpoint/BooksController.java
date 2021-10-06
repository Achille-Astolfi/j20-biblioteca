package com.example.library.app.bookendpoint;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    // il controller dichiara le dipendenze solo verso gli orchestrator
    private final GetBookSingleOrchestrator getBookSingleOrchestrator;
    private final PostBookCreateOtchestrator postBookCreateOrchestrator;

    @GetMapping("/{bookId}")
    ResponseEntity<BookResource> getBookSingle(@PathVariable Long bookId) {
        // Spring Web ha estratto tutti i valori dalla request http
        // adesso il metodo del controller può eseguire l'orchestrator
        Optional<BookResource> value = this.getBookSingleOrchestrator.getBookSingle(bookId);
        // ottenuto il risultato Java, il controller con l'aiuto di Spring Web
        // e della libreria Jackson trasforma il risultato Java in un
        // documento JSON e una response http
        return ResponseEntity.of(value);
    }

    @PostMapping
    ResponseEntity<?> postBookCreate(@RequestBody BookCreateDto dto){
        BookResource resource = this.postBookCreateOrchestrator.postBookCreate(dto);
        Long bookId = resource.getId();
        URI uri = fromMethodCall(on(this.getClass()).getBookSingle(bookId)).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
