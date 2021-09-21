package com.example.library.app.bookendpoint;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostBookCreateOrchestrator {
    private final BookService bookService;

    public BookResource postBookCreate(BookCreateDto dto) {
        // anche in questo caso l'orchestratore è banale
        return this.bookService.createBook(dto);
    }
}
