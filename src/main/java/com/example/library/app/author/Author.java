package com.example.library.app.author;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

//Mancano le annotation di Lombok
//il minimo sindacale suggerito dalla documentazione sarebbe @Getter e @AllArgsConstructor
//sappiamo invece che la strada più comoda è @Getter e @Setter col costruttore di default
@Getter
@Setter
//Esistono le annotation @Table e @Column
//bisogna fare un po' di esperimenti col db di prod èer capire se il
//value da specificare va scritto maiuscolo o minuscolo
//fatti esperimenti con Oracle, PostgreSQL: il maiuscolo funziona con tutti
//con MySql non funziona bisogna mettere minuscolo nelle annptations Table e Column e occorre mettere
//le doppie virgolette nell'sql dei test
//NB: le annotation vanno usate solo se strettamente necessarie
//qui le lasciamo solo per ricordare che esistono
@Table("author")
public class Author {
  //questo corrisponde alla colonna id se scritto id
  //ma diciamo che la preferenza è sempre quella di usare il camel case
  //corrispondente per evitare di riscrivere codice
  @Id
  @Column("author_id")
  public Long authorId;
  //In automatico Spring data jdbc trasforma i nomi delle colonne in snail_case,
  //nel corrispondente camelCase
  //questo corrisponde alla colonna last_name
  private String lastName;
  //questo corrisponde alla colonna first_name
  private String firstName;


}
