package com.example.library.model.author;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Create perché è il documento che specifica i dati da usare per creare una nuova resource
// Dto perché la class Java rappresenta un documento JSON il cui compito è quello di trasferire
// dati dal frontend al backend; DTO: Data Transfer Object
@Getter
@Setter // Setter dipende dalle impostazioni della libreria Jackson
@Data
public class AuthorCreateDto {
    // mi aspetto che il front end mi specifichi gli stessi dati con gli stessi nomi
    // cioè: se io gli rispondo con una resource contenente "firstName" e "lastName"
    // mi aspetto gli stessi nomi anche nel dto
    private String firstName;
    private String lastName;
}
