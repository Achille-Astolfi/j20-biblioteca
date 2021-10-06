package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorMapper;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {
  @Mapping(target="id", ignore = true)
  // trasferiamo authorId del Dto nel campo id dell'author dell'entity
  @Mapping(target = "author.id", source = "authorId")

  Book toEntity(BookCreateDto dto);
  BookResource toResource (Book entity);

  default String map(Author value){
    if(value == null) {
      return null;
    }
    return String.format("%s %s", value.getFirstName(), value.getLastName());
  }

  // per tutte le property che non voglio mappare ho la possibilità
  // di usare una annotation sul metodo
//  default Author map(Long authorId) {
//    if (authorId == null) {
//      return null;
//    }
//    return Author.builder()
//        .id(authorId)
//        .build();
//  }
//  @Mapping(target = "cavallo", ignore = true)






}
