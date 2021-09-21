package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.app.author.AuthorMapper;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {
  
  @Mapping(target = "id", ignore = true)
  //trasferiamo authorId del Dto nel campo id dell'author dell'entity
  @Mapping(target = "author.id", source = "authorId")
    //nel caso complesso abbiamo bisogno di un mapper più completo che vada a prendere i valori di firstName
    //e di lastName dall'AuthorCreateDto
  Book toEntity(BookCreateDto dto);

  //per tutte le property che non voglio mappare ho la possibilità di dichiarare una annotation
  //sul metodo: @Mapping(target = "cavallo", ignore = true)
  BookResource toResource(Book entity);
  //I metodi dell'interface che si chiamano map, quando devono fare corrispondenza con
  //metodi che non si accoppiano, invocano il get, invocano il map e poi fanno set da un'altra parte

  //l'implementazione standard non ci soddisfa perché restituisce sempre una stringa vuota
  //possiamo mettere un'implementazione con Java
  default String map(Author value){
    //implementazione abbozzata in BookServiceImpl
    if(value == null) {
      return null;
    }
    return String.format("%s %s", value.getFirstName(), value.getLastName());
  }


/*  default Author map(Long authorId){
    if(authorId == null) {
      return null;
    }
    return Author.builder()
        .id(authorId)
        .build();
  }*/

}
