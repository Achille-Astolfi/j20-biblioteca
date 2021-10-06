package com.example.library.app.bookendpoint;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import com.example.library.model.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostBookCreateOtchestrator {
  private final BookService bookService;

  // se vogliamo restituiro il body il metodo deve restituire AuthoResource
  // se invece voglioamo restituire "created" può essere sufficiente
  // restituire Long ossia l'id della risorsa creata.
  // NON è obbligatorio, ma ci aiuta a capisci se aggiungiamo le annotation
  // @NonNull al metodo e al parametro
  @NonNull
  public BookResource postBookCreate(@NonNull BookCreateDto dto){
    return this.bookService.createBook(dto);
  }
}
