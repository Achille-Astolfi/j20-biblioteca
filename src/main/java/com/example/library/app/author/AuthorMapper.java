package com.example.library.app.author;

import com.example.library.model.author.AuthorCreateDto;
import com.example.library.model.author.AuthorResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// la interface AuthorMapping che voglio definire come mapping di mapstruct
// funziona come un service (o un qualunque component) di Spring
// Come per Spring Data, anche MapStruct crea le class in modo automatico
// partendo dalle interface
// L'attributo componentModel con valore "spring" è il punto d'unione tra il  mondo
// MapStruct e il mondo Spring Framework
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    // per ogni metodo con un solo parametro e con return type non void
    // entrambi non primitivi
    // genera un metodo che fa quello che ci si aspetta: accoppia le property del
    // parametro con le property del tipo restituito e fa tutti i get dal parametro
    // e i set nel tipo restituito
    // Per convenzione i metodi inziano per "to"
    AuthorResource toResource(Author entity);

    // per creare un record di DB e lasciare al DB l'incombenza di assegnare l'id
    // sono certo al 100% che NON devo dare un valore alla property id dell'entity
    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorCreateDto dto);
}
