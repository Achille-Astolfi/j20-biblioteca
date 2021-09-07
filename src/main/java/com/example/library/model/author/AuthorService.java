package com.example.library.model.author;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AuthorService {
    Optional<AuthorResource> readAuthorById(@NonNull Long authorId);
}
