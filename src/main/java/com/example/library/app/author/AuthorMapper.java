package com.example.library.app.author;

import com.example.library.model.author.AuthorCreateDTO;
import com.example.library.model.author.AuthorResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AuthorMapper {
//molto probabilmente ho bisogno degli stessi mapper
  //devo tener presente che ho cambiato la struttura della mia entity class
  //1) potrei aver cambiato i nomi dei campi perché li scrivo proprio "uguali" ai nomi delle colonne
  //") non ho più i riferimenti ad altre entity ma non ho le foreing key secche
  //come campo
  //Quindi quasi certamente devo aggiungere/togliere/aggiornare le annotation
  //@Mappingconviene riscriverle da capo
  @Mapping(target = "id", source = "authorId")
  AuthorResource toResource (Author entity);

  @Mapping(target = "authorId", ignore = true)
  Author toEntity(AuthorCreateDTO dto);


}
