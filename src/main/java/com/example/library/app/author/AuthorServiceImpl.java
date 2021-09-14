package com.example.library.app.author;


import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  private final AuthorMapper authorMapper;

  @PostConstruct
  private void caricaAutori() {
    Author king = Author.builder().lastName("King").firstName("Stephen").build();
    Author rowling = Author.builder().lastName("Rowling").firstName("J. K.").build();
    king = this.authorRepository.save(king);
    rowling = this.authorRepository.save(rowling);
  }

  @Override
  public Optional<AuthorResource> readAuthorById(Long authorId) {
    Optional<Author> maybeAuthor = this.authorRepository.findById(authorId);
    return maybeAuthor.map(this::toResource);
  }

  @NonNull
  private AuthorResource toResource(@NonNull Author author){
//    AuthorResource resource = new AuthorResource();
//    resource.setId(author.getId());
//    resource.setFirstName(author.getFirstname());
//    resource.setLastName(author.getLastname());
//    return  resource;
    return this.authorMapper.toResource(author);
  }
}
