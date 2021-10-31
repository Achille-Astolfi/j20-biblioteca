package com.example.library.app.author;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;

  private final AuthorMapper authorMapper;

  @Override
  public Optional<AuthorResource> readAuthorById(Long authorId) {
    return this.authorRepository.findById(authorId)
        .map(this.authorMapper::toResource);
  }

  @Override
  @Transactional
  public AuthorResource createAuthor(AuthorCreateDto dto) {
    Author author = this.authorMapper.toEntity(dto);
    // ricordarsi sempre di riassegnare la variabile dopo il save
    author = this.authorRepository.save(author);
    return this.authorMapper.toResource(author);
  }
}


