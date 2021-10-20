package com.example.library.app.author;


import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
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
}
