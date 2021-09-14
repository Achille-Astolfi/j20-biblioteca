package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    // per tutte le property che non voglio mappare ho la possibilità di dichiarare una annotation
    // sul metodo
    @Mapping(target = "cavallo", ignore = true)
    BookResource toResource(Book entity);

    // l'implementazione standard non ci soddisfa
    // ci viene in aiuto Java 8 con i metodi default, a cui possiamo dare una implementazione
//    String map(Author value);
    default String map(Author value) {
        // l'implementazione l'avevamo abbozzata dentro il metodo di BookServiceImpl
        // possiamo addirittura risolvere il "exTODO"
        if (value == null) {
            return null;
        }
        return String.format("%s %s", value.getFirstName(), value.getLastName());
    }
}
