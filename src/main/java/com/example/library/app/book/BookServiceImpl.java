package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import com.sun.xml.bind.v2.TODO;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    // unisamo un metodo per caridare dei dati finti nel db
    // il metodo può essere rpivate , protector o public
    // annotato come PostConstructor verrà eseguito dopo il costruttore e dopo aver risolto tutte le dipendenze
    @PostConstruct
    private void caricaDatiFinti(){
        Author king = Author.builder().lastName("King").firstName("Stephen").build();
        Author rowling = Author.builder().lastName("Rowling").firstName("J. K.").build();
        // per inserire o aggiornare un record di DB si usa il metodo Save del repository
        // questo metodo è disponibile in tutte le impelemntazioni CRUD di spring data
        // è molto importare riaccegnare la variabile quando si usa il metodo save, perchè potrebbe essere un oggetto diverso,a nche se n
        // nella paggioranza dei casi è lo stesso oggetto
        king = this.authorRepository.save(king);
        rowling = this.authorRepository.save(rowling);
        Book it = new Book();
            it.setTitle("It");
            it.setAuthor(king);
        Book potter = new Book();
            potter.setAuthor(rowling);
            potter.setTitle("Harry Potter");
        it = this.bookRepository.save(it);
        potter = this.bookRepository.save(potter);

    }
    @Override
    public Optional<BookResource> readBookById(Long bookId) {
        // finId restituisce un optional empty se non esiste un record con chiave bookId
        // restituisce un optionl pieno se il record esiste
        Optional<Book> maybeBook = this.bookRepository.findById(bookId);
        return maybeBook.map(this::toResource);
    }

    //un metodo come parametro Book
    //come return type BookResurce
    // NonNull serve solo a ricordare che non deve essere null
    @NonNull
    private BookResource toResource(@NonNull Book book){
//        BookResource resource = new BookResource();
//        resource.setId(book.getId());
//        resource.setTitle(book.getTitle());
//        // TODO ci vorrebbe un metodo che controlli su getAtuthor() chenon sia null
//        resource.setAuthor(String.format("%s %s", book.getAuthor().getFirstname(), book.getAuthor().getLastname()));
//        return  resource;
        return this.bookMapper.toResource(book);
    }
}
