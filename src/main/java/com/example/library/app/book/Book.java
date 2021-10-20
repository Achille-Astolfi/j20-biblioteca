package com.example.library.app.book;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Book {
  @Id
  public Long bookId;
  private String title;
  //come funziona la foreign key?
  //anche Spring Data JDBC mi permette di usare associazioni verso
  //altre entity ma dobbiamo sapere che ogni volta che un Book viene aggiornato,
  //anche l'Author corrispondente viene CANCELLATO e REINSERITO
  //vuol dire che se nell'entity Author non mappo tutte le colonne della tabella author
  //i dati presenti nelle colonne non mappate vengono PERSI (sostituiti col default)
  //le foreign key le mappo con la colonna secca
  private Long authorId;
}
