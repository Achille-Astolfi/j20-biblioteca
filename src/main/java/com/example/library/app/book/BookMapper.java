package com.example.library.app.book;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    // questo è fisso, per via del create
    @Mapping(target = "bookId", ignore = true)
    // i limiti di Spring Data JDBC mi permettono da un'altra parte di semplificare
    // il lavoro sul mapper: il target authorId riceve (com'è giusto) il valore
    // del source authorId
    // title su title, va bene
    Book toEntity(BookCreateDto createDto);

    // bookId su id
    // title su title
    // ho un problema con author: posso risolverlo con una dipendenza da questo mapper
    // all'authorRepository ma lo sconsiglio (perché è un casino da implementare)
    // quindi andrà risolto nel BookServiceImpl
    @Mapping(target = "id", source = "bookId")
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "cavallo", ignore = true)
    BookResource toResource(Book entity);
}
