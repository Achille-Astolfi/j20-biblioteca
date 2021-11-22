package com.example.library.app.authorendpoint;

import java.util.List;
import java.util.Optional;

import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

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

    public List<AuthorResource> getAll() {
        return this.authorService.readAuthorsAll();
    }
}
