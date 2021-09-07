package com.example.library.model.author;

import com.sun.istack.NotNull;
import java.util.Optional;

public interface AuthorService {
   Optional<AuthorResource> readAuthorById(@NotNull Long authorId);
}
