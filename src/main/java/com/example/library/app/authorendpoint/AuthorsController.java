package com.example.library.app.authorendpoint;

import com.example.library.model.author.AuthorResource;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorsController {

  private final GetAuthorSingleOrchestrator getAuthorSingleOrchestrator;

  @GetMapping("/{authorId}")
  ResponseEntity<AuthorResource> getAuthorSingle(@PathVariable Long authorId) {
    Optional<AuthorResource> value = this.getAuthorSingleOrchestrator.getAuthorSingle(authorId);
    return ResponseEntity.of(value);
  }
}
