package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAuthorSingleOrchestrator {
    private final AuthorService authorService;

    public Optional<AuthorResource> getAuthorSingle(Long authorId) {
        // se proprio proprio vogliamo essere precisi
        return Optional.ofNullable(authorId)
                .flatMap(this.authorService::readAuthorById);
        // va bene lo stesso scrivere questo, fa la stessa cosa:
//        if (authorId == null) {
//            return Optional.empty();
//        }
//        return this.authorService.readAuthorById(authorId);
    }
}
