package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAuthorCreateOrchestrator {
  private final AuthorService authorService;

  //NB se vogliamo restituire il body allora il metodo deve restituire AuthorResource
  //NB se vogliamo restituire "created", può essere sufficiente restituire Long ossia
  //l'id della risorsa creata
  //NON è obbligatorio, ma ci aiuta a capirci se aggiungiamo le annotation
  //@NonNull di springframework Lang al metodo e al paramaetro
  @NonNull
  public AuthorResource postAuthorCreate(@NonNull AuthorCreateDTO authorCreateDTO) {
    //in questo caso l'orchestratore è banale
    return this.authorService.createAuthor(authorCreateDTO);
  }
}
