package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface BookMapper {
  // per tutte l eproperty che non voglio mappare ho la possibilità
  // di usare una annotation sul metodo

  @Mapping(target = "cavallo", ignore = true)
  BookResource toResource (Book entity);

  default String map (Author value){
    if (value ==null){
      return null;
    }
    return String.format("%s %s", value.getFirstName() + value.getLastName());
  }

}
