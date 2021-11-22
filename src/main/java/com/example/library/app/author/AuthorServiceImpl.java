package com.example.library.app.author;


import java.util.List;
import java.util.Optional;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public Optional<AuthorResource> readAuthorById(Long authorId) {
        // con gli optional
        return this.authorRepository.findById(authorId)
                .map(this.authorMapper::toResource);
        // senza gli optional
//        Optional<Author> author = this.authorRepository.findById(authorId);
//        if (author.isEmpty()) {
//            return Optional.empty();
//        } else {
//            AuthorResource authorResource = this.authorMapper.toResource(author.get());
//            return Optional.of(authorResource);
//        }
    }

    @Override
    @Transactional
    public AuthorResource createAuthor(@NonNull AuthorCreateDto dto) {
        Author author = this.authorMapper.toEntity(dto);
        // ricordarsi SEMPRE di riassegnare la variabile dopo save
        author = this.authorRepository.save(author);
        return this.authorMapper.toResource(author);
    }

    @Override
    @Transactional
    public AuthorResource updateAuthor(Long authorId, @NonNull AuthorCreateDto dto) {
        var entity = this.authorRepository.findById(authorId).orElseThrow();
        entity = this.authorMapper.updateEntity(dto, entity);
        // ricordarsi SEMPRE di riassegnare la variabile dopo save
        entity = this.authorRepository.save(entity);
        return this.authorMapper.toResource(entity);
    }

    @Override
    public List<AuthorResource> readAuthorsAll() {
        var entities = this.authorRepository.findAll();
        return this.authorMapper.toResourceList(entities);
    }
}
