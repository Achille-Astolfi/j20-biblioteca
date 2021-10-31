package com.example.library.model.author;

import java.util.Optional;

public interface AuthorService {
   Optional<AuthorResource> readAuthorById (Long authorId);
   AuthorResource createAuthor(AuthorCreateDto dto);
}
