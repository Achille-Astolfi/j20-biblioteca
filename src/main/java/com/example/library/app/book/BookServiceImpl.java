package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookResource> readBookById(@NonNull Long bookId) {
        return this.bookRepository.findById(bookId)
                .map(this::toResourceImpl);
    }

    @Override
    @Transactional
    @NonNull
    public BookResource createBook(@NonNull BookCreateDto dto) {
        Book book = this.bookMapper.toEntity(dto);
        // ricordarsi sempre di riassegnare la variabile
        book = this.bookRepository.save(book);
        //
        return this.toResourceImpl(book);
    }

    // il metodo del bookMapper non è sufficiente per completare i dati di BookResource
    // quindi mi creo un metodo di appoggio private che fa l'operazione
    private BookResource toResourceImpl(@NonNull Book book) {
        // primo passo: uso il mapper per tirarmi fuori BookResource SENZA il campo author
        BookResource bookResource = this.bookMapper.toResource(book);
        // secondo passo uso book per tirarmi fuori la foreign key per trovare
        // l'author grazie a authorRepository
        // MI fido del DB: siccome author_id è una foreign key di DB, allora sono SICURO che trovo il record
        // RISCHIO una NoSuchElementException
        Author author = this.authorRepository.findById(book.getAuthorId()).get();
        // terzo passo: calcolo nome + cognome e imposto il valore di author dentro BookResource
        // anche questo è migliorabile, perché firstName può essere null
        String fullName = String.format("%s %s", author.getFirstName(), author.getLastName());
        bookResource.setAuthor(fullName);
        return bookResource;
//        // in alternativa va benissimo usare un ifPresent(): non rischio NoSuchElementException
//        // rischio però di avere un NullPointerException in seguito, su bookResource.getAuthor()
//        Optional<Author> author = this.authorRepository.findById(book.getAuthorId());
//        if (author.isPresent()) {
//            // terzo passo
//        }
    }
}
