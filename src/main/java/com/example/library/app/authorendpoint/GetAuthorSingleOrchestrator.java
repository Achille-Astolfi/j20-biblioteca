package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAuthorSingleOrchestrator {

  private final AuthorService authorService;

  //flatmap introduce il controllo sul null
  public Optional<AuthorResource> getAuthorSingle (Long authorId) {
    return Optional.ofNullable(authorId)
        .flatMap(this.authorService::readAuthorById);
  }
}
