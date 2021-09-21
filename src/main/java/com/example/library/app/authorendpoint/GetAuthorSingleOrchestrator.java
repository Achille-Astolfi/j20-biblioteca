package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RequiredArgsConstructor
public class GetAuthorSingleOrchestrator {
  private final AuthorService authorService;

  public Optional<AuthorResource> getAuthorSingle(Long authorId) {

    return Optional.ofNullable(authorId)
        .flatMap(this.authorService::readAuthorById);

//        if (authorId == null) {
//            return Optional.empty();
//        }
//        return this.authorService.readAuthorById(authorId);
  }

}
