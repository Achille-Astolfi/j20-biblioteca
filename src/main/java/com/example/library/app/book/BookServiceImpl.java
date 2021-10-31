package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
        return this.bookRepository.findById(bookId).map(this::toResourceImpl);
    }

    @Override
    @Transactional
    @NonNull
    public BookResource createBook(BookCreateDto dto) {
        Book book = this.bookMapper.toEntity(dto);
        book = this.bookRepository.save(book);
        return this.toResourceImpl(book) ;
    }

    private BookResource toResourceImpl(Book book){
        // primo passo : uso il mapper per recuperare il BookResource senza il campo author
        BookResource bookService = this.bookMapper.toResource(book);
        // seocndo passo usa il book per recuperare la foreign key per torvare l'author grazie a authorRepository
        Author author = this.authorRepository.findById(book.getBookId()).get();
        // terzo passo calcolo nome + cognome e imposto il valore di author dentro BookResource
        String fullname = String.format("%s %s", author.getFirstName(), author.getLastName());
        bookService.setAuthor(fullname);
        return bookService;
    }

}
