package com.example.library.model.author;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

public interface AuthorService {
    Optional<AuthorResource> readAuthorById(@NonNull Long authorId);

    // qui è filosofia: posso decidere di passare come parametri
    // i singoli campi (firstName e lastName) oppure posso decidere
    // di passare il Dto
    // A noi piace il filosofo del Dto.
    // Poi: vuoi la risposta 200? Restituisco AuthorResource
    // Vuoi la risposta Accepted? Restituisco void
    // Vuoi la risposta Created? Restituisco Long (l'id assegnato alla resource)
    AuthorResource createAuthor(AuthorCreateDto dto);
    AuthorResource updateAuthor(Long authorId, AuthorCreateDto dto);

    List<AuthorResource> readAuthorsAll();
}
