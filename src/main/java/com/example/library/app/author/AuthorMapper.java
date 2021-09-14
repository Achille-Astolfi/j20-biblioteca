package com.example.library.app.author;

import com.example.library.model.author.AuthorResource;
import org.mapstruct.Mapper;

//La interface AuthorMapping che voglio definire come mapping di mapstruct
//funziona come un qualunque component di Spring
//Come per Spring Data anche MapStruct crea le classi in modo automatico
//partendo dalle interface
//L'attributo componentModel co valore "spring" è il punto di unione tra il mondo
//MapStruct e Spring Framework
@Mapper(componentModel = "spring")
public interface AuthorMapper {
  //per ogni metodo con un solo parametro con return type non void, entrambi non primitivo
  //genera un metodo che fa quello che ci si aspetta:
  //accoppia le property del parametro con le property del tipo restituito
  //e fa tutti i get dal parametro e i set nel tipo restituito
  //Per convenzione i metodi iniziano per "to"
  //Viene generato l'AuthorMapperImpl in target/generated-sources
  AuthorResource toResource (Author entity);


}
