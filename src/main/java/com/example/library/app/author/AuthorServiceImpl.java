
package com.example.library.app.author;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;
  //Dichiaro la dipendenza normalmente
  private final AuthorMapper authorMapper;
//  @PostConstruct
//  private void caricaAutori() {
//    Author king = Author.builder().lastName("King").firstName("Stephen").build();
//    Author rowling = Author.builder().lastName("Rowling").firstName("J. K.").build();
//    king = this.authorRepository.save(king);
//    rowling = this.authorRepository.save(rowling);
//  }



  @Override
  public Optional<AuthorResource> readAuthorById(Long authorId) {
    Optional<Author> maybeAuthor = this.authorRepository.findById(authorId);
    return maybeAuthor.map(this::toResource);
  }

  @Override
  @Transactional
  //per fare delle operazioni sul DB non in sola lettura,
  // ma per creare un nuovo record sulla tabella (author in questo caso)

  public AuthorResource createAuthor(AuthorCreateDto dto) {
    // on toEntity si crea la entity da inserire nel DB
    Author entity = this.authorMapper.toEntity(dto);
    // importante riassegnare il valore entity
    entity = this.authorRepository.save(entity);
    // con toResourse creo la resourse ossia l'oggetto che rappresenta l'entity
    return this.authorMapper.toResource(entity);
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
