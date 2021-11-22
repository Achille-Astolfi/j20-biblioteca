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

    // nota bene: se vogliamo restituire il body, allora il metodo deve restituire
    // AuthorResource
    // nota bene: se invece vogliamo restituire "created" può essere sufficiente
    // restituire Long ossia l'id della risorsa creata
    // NON è obbligatorio, ma ci aiuta a capirci se aggiungiamo le annotation
    // @NonNull al metodo e al parametro
    @NonNull
    public AuthorResource postAuthorCreate(@NonNull AuthorCreateDto authorCreateDto) {
        // anche in questo caso l'orchestratore è banale
        return this.authorService.createAuthor(authorCreateDto);
    }

    @NonNull
    public AuthorResource patchAuthorUpdate(Long authorId, @NonNull AuthorCreateDto authorCreateDto) {
        // anche in questo caso l'orchestratore è banale
        return this.authorService.updateAuthor(authorId, authorCreateDto);
    }
}
