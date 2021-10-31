package com.example.library.app.author;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

// esisistono le annotation @Table e @Column
// bisogna fare un po' di esperimenti con il DB di produzione
// il value da specificare va scritto in maiuscolo o minuscolo
// fatti esperimenti con mySQL, Oravle postgresSQL: il maiuscolo funziona con tutti
// NB le annotation vanno usate solo se strettamente necessarie
// le annotation sulle clas restano solo perchè "esistono" ma non vengono utlizzate
@Table("author")
@Getter
@Setter
public class Author {
  // scritta così corrisponde alla colonna id
  // la preferenza è sempre quella di usare il camelCase corrispondente
  // per evitare di riscrivere codice
  @Column("author_id")
  @Id
  public Long authorId;
  // in automatico Spring data JDBC trasforma i nome delle colonne 5
  // in snail_case nei campi con il corrispondente camelcase:
  // corrisponde alla colonna last_name
  private String lastName;
  // corrisponde alla colonna first_name
  private String firstName;

}
