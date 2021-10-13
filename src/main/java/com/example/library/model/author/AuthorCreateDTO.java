package com.example.library.model.author;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//create perché è il documento che specifica i dati da usare per creare una nuova resource
//DTO perché la class Java rappresenta un documento JSON il cuo compito è quello
//di trasferire dati dal frontend al backend; DTO: Data Transfer Object
@Setter //dipendente dalle impostazioni della libreria Jackson
@Getter
public class AuthorCreateDTO {
  //mi aspetto che il frontend mi specifichi gli stessi dati con gli stessi nomi
  //cioè: se io gli rispondo con una resource contenente "firstName" e "lastName"
  //mi aspetto gli stessi nomi anche nel DTO
  private String firstName;
  private String lastName;

}
