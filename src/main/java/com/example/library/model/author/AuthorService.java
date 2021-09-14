package com.example.library.model.author;

import java.util.Optional;

public interface AuthorService {

  Optional<AuthorResource> readAuthorById (Long authorId);

  //qui è filosofia: posso decidere come parametri i singoli campi (fistName e lastName)
  //oppure decidere di passare il DTO, meglio il DTO
  //Vuoi il 200? Restituisco AuthorResource
  //Vuoi ACCEPTED? Allora void
  //Vuoi CREATED? Allora Long, cioè l'id assegnato
  AuthorResource createAuthor(AuthorCreateDTO dto);
}
