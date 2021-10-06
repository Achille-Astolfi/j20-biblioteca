package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAuthorCreateOrchestrator {
  private final AuthorService authorService;

  // se vogliamo restituiro il body il metodo deve restituire AuthoResource
  // se invece voglioamo restituire "created" può essere sufficiente
  // restituire Long ossia l'id della risorsa creata.
  // NON è obbligatorio, ma ci aiuta a capisci se aggiungiamo le annotation
  // @NonNull al metodo e al parametro
  @NonNull
  public AuthorResource postAuthorCreate(@NonNull AuthorCreateDto authorCreateDto) {
    return this.authorService.createAuthor(authorCreateDto);
   }

}
