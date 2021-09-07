package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    //Usiamo un metodo per caricare dei dati finti nel DB, tanto ogni volta vengono azzerati
    //Il metodo può essere private "package protected" protected o public
    //Lo annoto con PostConstruct; viene invocato da Spring dopo il costruttore e dopo aver
    //risolto tutte le dipendenze
    @PostConstruct
    private void caricaDatiFinti() {
        Author king = Author.builder()
            .lastName("King")
            .firstName("Stephen")
            .build();
        Author rowling = Author.builder()
            .lastName("Rowling")
            .firstName("J. K.")
            .build();
        //per inserire o aggiornare un record di DB si usa il metodo save del repository
        //questo metodo è disponibile in tutte le implementazioni CRUD di Spring Data
        //NOTA BENE: è molto importante riassegnare la variabile quando si invoca il metodo save:
        //nella maggioranza dei casi è lo stesso oggetto ma PORTEBBE essere un pggetto doverso
        king = this.authorRepository.save(king);
        rowling = this.authorRepository.save(rowling);

        Book it = new Book();
        it.setAuthor(king);
        it.setTitle("It");
        Book potter = new Book();
        potter.setAuthor(rowling);
        potter.setTitle("Harry Potter e la pietra filosofale");

        it = this.bookRepository.save(it);
        potter = this.bookRepository.save(potter);
    }

    @Override
    public Optional<BookResource> readBookById(Long bookId) {
        Optional<Book> maybeBook = this.bookRepository.findById(bookId);
        //maybeBook ha un metodo map che prende in argomento un method reference
        return maybeBook.map(this::toResource);
    }

    //per semplificarci la vita scriviamo un metodo che ha
    //come parametro un book e come return type un resource
    //l'annotation @NonNull serve a ricordarci cosa ci aspettiamo che possa essere null
    //e che cosa no. Questa annotation viene usata da strumenti come Sonar (SonarQube, SonarLint ecc)
    //che arrivano ad analizzare anche queste annotation, dando dei warning se è necessario darli
    @NonNull
    private BookResource toResource(@NonNull Book book) {
        //fa a mano quello che farà il mapstruct
        BookResource resource = new BookResource();
        resource.setId(book.getId());
        resource.setTitle(book.getTitle());
        //TODO: ci vorrebbe un controllo su getAuthor che non sia null
        resource.setAuthor(String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
        return resource;

    }
}
