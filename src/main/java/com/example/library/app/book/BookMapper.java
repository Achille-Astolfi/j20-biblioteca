package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

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
}
