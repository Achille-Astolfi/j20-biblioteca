package com.example.library.app.author;

// la interface AuthorMapper (mapping mapstruct funziona come un service di spring
// (o un qualunque component)
// come per spring data, anche mapStrunc crea le class in automatico partendo dalle interface
// per essere riconosciute da mapstruct le interface devono avere un annotation
// con Mapper dice che funziona come un repository
// l'attributo componentModel con valore "spring" è il punto di unione tra il mondo MapStruct e Spring

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface AuthorMapper {
  // per ogni metodo con un solo parametro con return type NON void
  //  entrambi non primitivi
  // genera un metodo che ci si aspetta: accoppia le property del parametro con le property del tipo restituito
  //  e fa tutti i get dal parametro e i set nel tipo restituito
  // per convenzione i metodi iniziano per "to"

  AuthorResource toResource(Author entity);
  @Mapping(target = "id", ignore = true)
  Author toEntity(AuthorCreateDto dto);
}
