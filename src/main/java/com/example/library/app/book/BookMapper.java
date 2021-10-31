package com.example.library.app.book;

import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target ="bookId", ignore = true)

  Book toEntity(BookCreateDto dto);

  @Mapping(target = "id", source = "bookId")
  @Mapping(target = "author", ignore = true)
  BookResource toResource (Book entity);


}
