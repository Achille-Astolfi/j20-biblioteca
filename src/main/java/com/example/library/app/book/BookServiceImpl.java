package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
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

    @Override
    @Transactional
    public BookResource createBook(BookCreateDto dto) {
        Book book = this.bookMapper.toEntity(dto);
        //quando si lavora con JPA le cose devono essere fatte con cautela..
        //Il mapper crea un oggetto (new) Author che però riferisce
        //un refcord di DB già esistente; questa non va bene, perché
        //se il record di SB esiste, l'oggetto Author deve essere
        //creato (new) da Hibernate (o chi per esso).
        //NB: a noi non interessa fare veramente la select sulla tabella author
        //a noi interessa solo avere l'oggetto gestito da Hibernate
        Long idEsistente = book.getAuthor().getId(); //NON è vero
        //l'oggetto esistente è quello che ottengo facendo getOne dal repository
        //NB: nel caso complicato se idEsistente == null allora creo un nuovo author
        //altrimenti procedo come nel resto di questo metodo
        Author esistente = this.authorRepository.getOne(idEsistente);
        //se Hibernate ha già creato l'oggetto Author corrispondente all'id,
        //mi restituisce l'oggetto, se invece non l'ha creato, Hibernate restituisce
        //un "proxy" dell'oggetto; solo quando faremo per esempio esistente.getFirstName()
        //allora Hibernate eseguirà la SELECT sul DB
        //In nessuno dei due csi quindi il metodo getOne fa SELECT sul DB
        //esistente è diverso da book.detAuthor(), quindi lo devo riassegnare
        book.setAuthor(esistente);
        book = this.bookRepository.save(book);
        return toResource(book);
    }

    //per semplificarci la vita scriviamo un metodo che ha
    //come parametro un book e come return type un resource
    //l'annotation @NonNull serve a ricordarci cosa ci aspettiamo che possa essere null
    //e che cosa no. Questa annotation viene usata da strumenti come Sonar (SonarQube, SonarLint ecc)
    //che arrivano ad analizzare anche queste annotation, dando dei warning se è necessario darli
    @NonNull
    private BookResource toResource(@NonNull Book book) {
        //fa a mano quello che farà il mapstruct
       /* BookResource resource = new BookResource();
        resource.setId(book.getId());
        resource.setTitle(book.getTitle());
        //TODO: ci vorrebbe un controllo su getAuthor che non sia null
        resource.setAuthor(String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
        return resource;*/
        //mapstruct
        return this.bookMapper.toResource(book);

    }
}
