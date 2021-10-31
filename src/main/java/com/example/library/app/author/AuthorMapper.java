package com.example.library.app.author;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
  // molto probabilmente ho bisogno degli stessi metodi mapper
  // tenendo conto del cambiamento nella struttura dell'entity class
  // 1) potrei aver cambiato i nomi dei campi perchè li scrivo "uguali" ai omi delle colonne
  // 2) non ho più i riferimenti a altre entity, ma ho le foreign key secce come ogni campo
  // quindi devo aggiuungere/togliere/aggiornare le annotation
  // @Mpapping: probabilmente conviene riscriverle da capo
  @Mapping(target = "id", source = "authorId")
  AuthorResource toResource(Author entity);

  @Mapping(target = "authorId", ignore = true)
  Author toEntity(AuthorCreateDto dto);
}
