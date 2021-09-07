package com.example.library.app.bookendpoint;

import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

// in realtà può andar bene anche l'annotation @Service
// mi serve per distinguere i service con l'interface dai service senza interface
@Component
@RequiredArgsConstructor
public class GetBookSingleOrchestrator {
    // l'orchestrator dichiara le dipendenze da tutti i service necessari
    private final BookService bookService;

    // non essendoci un'interface e trovandoci nello stesso package, potrei anche
    // decidere di dichiarare i metodi "package protected" oppure protected
    public Optional<BookResource> getBookSingle(Long bookId) {
        // in un ambito didattico come questo l'orchestrator non ha molto da fare
        // perché dipende da un unico service che fa già tutto quello che serve
        return this.bookService.readBookById(bookId);
    }
}
