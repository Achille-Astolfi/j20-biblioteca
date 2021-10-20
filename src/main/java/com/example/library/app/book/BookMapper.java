package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorMapper;
import com.example.library.app.author.AuthorRepository;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

  //questo è fisso, per via del create: se devo creare il bookId lo devo lasciare null
  @Mapping(target = "bookId", ignore = true)
  //I limiti di JDBC mi permettono da un'altra parte di semplificare il lavoro
  //sul mapper: il target authorId riceve (come è giusto) il valore
  //del source authorId
  //title su title va bene
  Book toEntity(BookCreateDto dto);

  //bookId su id
  //title su title
  //ho un problema con author: posso risolverlo con una dipendenza qui sul repository
  //mettendo @Mapper(componentModel = "spring", uses = AuthorRepository.class) ma Achille lo sconsiglia
  //perché è difficile da implementare
  //quindi andrà risolto nel BookSserviceImpl
  @Mapping(target = "id", source = "bookId")
  @Mapping(target = "author", ignore = true)
  BookResource toResource(Book entity);



}
