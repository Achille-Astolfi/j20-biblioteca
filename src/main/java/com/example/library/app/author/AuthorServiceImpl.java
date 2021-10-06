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

    // posso dichiarare la dipendenza sul mapper di MapStruct come una normale dipendenza
    // Spring - sul tipo interface
    private final AuthorMapper authorMapper;

    @Override
    public Optional<AuthorResource> readAuthorById(Long authorId) {
        Optional<Author> maybeAuthor = this.authorRepository.findById(authorId);
        return maybeAuthor.map(this::toResource);
    }

    @Override
    @Transactional
    public AuthorResource createAuthor(AuthorCreateDto dto) {
        // abbiamo le dipendenze, abbiamo i metodi
        // con toEntity creo la entity da inserire nel DB
        Author entity = this.authorMapper.toEntity(dto);
        // è importantissimo riassegnare il valore di entity
        entity = this.authorRepository.save(entity);
        // con toResource creo la resource ossia l'oggetto che rappresenta
        // l'entity
        return this.authorMapper.toResource(entity);
    }

    @NonNull
    private AuthorResource toResource(@NonNull Author author) {
//        AuthorResource resource = new AuthorResource();
//        resource.setId(author.getId());
//        resource.setFirstName(author.getFirstName());
//        resource.setLastName(author.getLastName());
//        return resource;
        return this.authorMapper.toResource(author);
    }
}
