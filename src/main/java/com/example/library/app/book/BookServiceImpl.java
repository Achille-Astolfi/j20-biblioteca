package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    // aggiungo nelle dipendenze il mapper di mapStruct
    private final BookMapper bookMapper;

    @Override
    public Optional<BookResource> readBookById(Long bookId) {
        // findById restituisce Optional.empty() se non esiste un record con chiave bookId
        // restituisce un Optional pieno se il record esiste
        Optional<Book> maybeBook = this.bookRepository.findById(bookId);
        // maybeBook ha un metodo map che prende come argomento un method reference
        return maybeBook.map(this::toResource);
    }

    @Override
    @Transactional
    public BookResource createBook(BookCreateDto dto) {
        // abbiamo i metodi e abbiamo le dipendenze
        Book book = this.bookMapper.toEntity(dto);
        // MA...
        // quando si lavora con JPA le cose devono essere fatte con cautela
        // Il mapper crea (new) un oggetto Author che però riferisce un
        // record di database già esistente; questo non va bene, perché
        // se il record di database esiste, l'oggetto Author deve essere
        // creato (new) da Hibernate (o chi per esso).
        // NOTA BENE: a noi non interessa fare davvero la select sulla tabella author
        // a noi interessa avere l'oggetto gestito da Hibernate (o...)
        Long idEsistente = book.getAuthor().getId();
        // NB: nel caso complicato: se idEsistente == null allora creo un nuovo author
        //     altrimenti procedo come nel resto di questo metodo
        Author esistente = this.authorRepository.getOne(idEsistente);
        // se Hibernate ha già creato l'oggetto Author corrispondente all'id, mi restituisce l'oggetto
        // se invece non l'ha creato, Hibernate restituisce un "proxy" dell'oggetto; solo quando
        // per esempio faremo esistente.getFirstName() allora Hibernate eseguirà la SELECT sul DB
        // Il risultato è che in nessuno dei due casi il metodo getOne fa SELECT sul DB
        // esistente è diverso da book.getAuthor(), quindi lo devo riassegnare
        book.setAuthor(esistente);
        // ricordarsi di riassegnare la variabile
        book = this.bookRepository.save(book);
        // adesso posso trasformare la entity in resource
        return this.bookMapper.toResource(book);
    }

    // per semplificarci la vita scriviamo un metodo che ha come parametro Book
    // e come return type BookResource
    // L'annotation NonNull di Spring Framework non serve a niente se non a ricordarci
    // che cosa ci aspettiamo che possa essere null e che cosa no.
    // Strumenti come Sonar (SonarQube, SonarLint, ecc) arrivano ad analizzare anche queste
    // annotation, dando dei warning se è necessario darli
    @NonNull
    private BookResource toResource(@NonNull Book book) {
//        // facciamo a manina quello che poi faremo fare a mapstruct
//        BookResource resource = new BookResource();
//        resource.setId(book.getId());
//        resource.setTitle(book.getTitle());
//        // exTODO: ci vorrebbe un controllo su getAuthor() che non sia null
//        resource.setAuthor(String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
//        return resource;
        return this.bookMapper.toResource(book);
    }

}
