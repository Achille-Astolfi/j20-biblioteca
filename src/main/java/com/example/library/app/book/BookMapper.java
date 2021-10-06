package com.example.library.app.book;

import com.example.library.app.author.Author;
import com.example.library.model.book.BookCreateDto;
import com.example.library.model.book.BookResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    // trasferiamo authorId del Dto nel campo id dell'author dell'entity
    // nel caso complesso, abbiamo bisogno di un mapper più completo che mi vada
    // a prendere i valori di firstName e lastName dall'AuthorCreateDto
    @Mapping(target = "author", source = "authorId")
    Book toEntity(BookCreateDto createDto);

    // da capire perché con una build incrementale questo metodo viene generato
    // in automatico mentre con una rebuild questo metodo non viene generato
    default Author map(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return Author.builder()
                .id(authorId)
                .build();
    }

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
