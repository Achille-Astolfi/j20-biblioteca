package com.example.library.app.bookendpoint;

import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostBookCreateOrchestrator {
  private final BookService bookService;

  public BookResource postBookCreate(BookCreateDto bookCreateDto) {
    return this.bookService.createBook(bookCreateDto);
  }

}
