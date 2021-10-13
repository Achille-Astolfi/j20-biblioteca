package com.example.library.model.author;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// create perchè è il documento che specifica i dati da usare per creare una nuova resource
// Dto perchè la clas Java rappresenta un nuovo documento JSON il cui compito
// è quello di trasperire dati dal frontend al backend
// DTO data transfer object
@Getter
@Setter //dipende dalla impostazioni della libreria di Jackson
@Data
public class AuthorCreateDto {

  private String firstName;
  private String lastName;

}
