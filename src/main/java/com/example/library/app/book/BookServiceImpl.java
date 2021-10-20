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
    public Optional<BookResource> readBookById(Long bookId) {
        return this.bookRepository.findById(bookId)
            .map(this::toResourceImpl);
    }
    @Override
    @Transactional
    @NonNull
    public BookResource createBook(BookCreateDto dto) {
        Book book = this.bookRepository.save(this.bookMapper.toEntity(dto));
        return this.toResourceImpl(book);
    }

    //Il metodo del bookMapper non è sufficiente per completare i dati di BookResource
    //quindi mi crea un metodo di appoggio private che fa l'operazione
    private BookResource toResourceImpl(@NonNull Book book) {
        //primo passo: uso il mapper per ricavare il BookResource senza il campo author
        BookResource bookResource = this.bookMapper.toResource(book);
        //secondo passo: uso book per tirare fuori la foreign key
        //tramite l'authorRepository mi faccio dare l'author
        Optional<Author> author = authorRepository.findById(book.getAuthorId()); //se metto .get() qui rischio una eccezione
        //terzo passo: calcolo nome e cognome dell'author del repository e imposto il valore dentro BookResource
        author.ifPresent(value -> bookResource.setAuthor(value.getFirstName() + value.getLastName()));
        return  bookResource;
    }
}
