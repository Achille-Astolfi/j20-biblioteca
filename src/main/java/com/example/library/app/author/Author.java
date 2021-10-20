package com.example.library.app.author;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

// esistono le annotation @Table e @Column
// bisogna fare un po' di esperimenti con il DB di produzione per capire se
// il value da specificare va scritto maiuscolo o minuscolo;
// fatti esperimenti con Oracle, PostgreSQL, DB2, H2: il maiuscolo funziona con tutti
// MySQL non funziona, occorre mettere minuscolo, e poi occorre mettere le doppie virgolette
// nelle sql dei test.
// NOTA BENE: le annotation vanno usate solo se strettamente necessario
// vi lascio le annotation sulla class solo per dirvi che esistono
@Table("author")
// mancano le annotation di Lombok
// il minimo sindacale suggerito dalla documentazione è @Getter e @AllArgsConstructor
// invece sappiamo che la strada più comoda è @Getter e @Setter col costruttore di default
@Getter
@Setter
public class Author {
    // scritta com'era prima corrisponderebbe alla colonna id
    // diciamo che la preferenza è sempre quella di usare il camelCase corrispondente
    // per evitare il più possibile di riscrivere codice
    @Column("author_id")
    // manca l'annotation @Id
    @Id
    private Long authorId;
    // in automatico Spring Data JDBC trasforma i nomi delle colonne in
    // snail_case nei campi con il corrispondente camelCase:
    // corrisponde alla colonna last_name
    private String lastName;
    // corrisponde alla colonna first_name
    private String firstName;
}
