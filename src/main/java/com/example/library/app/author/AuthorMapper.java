package com.example.library.app.author;

import java.util.List;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    // molto probabilmente ho bisogno degli stessi metodi mapper
    // devo tener presente che ho cambiato la struttura della mia entity class
    // 1) potrei aver cambiato i nomi dei campi perché li scrivo proprio "uguali"
    //    ai nomi delle colonne
    // 2) non ho più i riferimenti ad altre entity ma ho le foreign key secche
    //    come campo
    // Quindi quasi certamente devo aggiungere/togliere/aggiornare le annotation
    // @Mapping; molto probabilmente mi conviene riscriverle daccapo
    @Mapping(target = "id", source = "authorId")
    AuthorResource toResource(Author entity);

    List<AuthorResource> toResourceList(Iterable<Author> entities);

    @Mapping(target = "authorId", ignore = true)
    Author toEntity(AuthorCreateDto dto);

    @Mapping(target = "authorId", ignore = true)
    Author updateEntity(AuthorCreateDto dto, @MappingTarget Author existing);
}
