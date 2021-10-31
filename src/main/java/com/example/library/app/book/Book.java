package com.example.library.app.book;

import com.example.library.app.author.Author;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Book {
  @Id
  public Long bookId;
  private String title;
  // come funziona per le foreign key?
  //  private Author author;
  // anche spring data JDBC mi permetterebber di usare associazioni verso
  // altre entity, ma dobbiamo sapere che: ogni volta che un book viene agigonrato
  // succede che author corrispondente iene cancellato e reinserito
  // vuol dire ce se nell'entity author non mappo tutte le colone della tabella author
  // i dati presenti nelle colonne non mappate vengono persi (sostituiti con il default)
  // quindi le foreign key le mappo con la colonna secca
  private Long authorId;
}
