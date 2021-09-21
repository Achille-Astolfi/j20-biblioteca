package com.example.library.app.author;

import com.example.library.app.book.Book;
import com.example.library.app.book.BookRepository;
import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import com.example.library.model.book.BookResource;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

//Il motivo per cui si usa mapstruct è per evitare che author che è un'entity venga utilizzato
//come ritorno dell'authorsController, che come webserver rest,
//deve restituire una rappresentazione di una risorsa, che nello standard di fatto,
//è un documento JSON. La risorsa viene creata come modello di oggetto di ritorno

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
  private final AuthorRepository authorRepository;
  //Dichiaro la dipendenza normalmente
  private final AuthorMapper authorMapper;

  @Override
  public Optional<AuthorResource> readAuthorById(Long authorId) {
    Optional<Author> maybeAuthor = this.authorRepository.findById(authorId);
    return maybeAuthor.map(this::toResource);
  }

  @Override
  @Transactional()
  public AuthorResource createAuthor(AuthorCreateDTO dto) {
    //TODO
    return null;
  }

  //Mapstruct mi permette di fare questa operazione in modo automatico
  //Se  ho  un oggetto Author e voglio produrre un oggetto AuthorResource
  //posso farlo agevolmente sfruttando le proprietà dei JavaBean
  //Noi costruiamo i JavaBean con Lombok e in questo caso per ogni coppia getNome setNome
  //che abbia il nome di ritorno del get con il nome settato dal set, costutuisce una
  //property di un JavaBean: Lombok ci dà una corrispondenza tra i campi delle classe
  //e le proprietà dei JavaBean
  //Per ogni coppia di Author e AuthorReource fa esattamente quello che fa qusto metodo
  //ma in modo automatico per ogni coppia
  @NonNull
  /*private AuthorResource toResource(@NonNull Author author){
    AuthorResource resource = new AuthorResource();
    resource.setId(author.getId());
    resource.setFirstName(author.getFirstName());
    resource.setLastName(author.getLastName());
    return resource;
  }*/
  private AuthorResource toResource(@NonNull Author author) {
    return this.authorMapper.toResource(author);
  }
}
