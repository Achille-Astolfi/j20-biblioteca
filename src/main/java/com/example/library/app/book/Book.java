package com.example.library.app.book;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Book {
    @Id
    private Long bookId;
    private String title;
    // come funziona per le foreign key?
    // Anche Spring Data JDBC mi permetterebbe di usare associazioni verso
    // altre entity, ma dobbiamo sapere che: ogni volta che un Book viene aggiornato
    // succede che l'Author corrispondente viene CANCELLATO E REINSERITO
    // Vuol dire che se nell'entity Author non mappo tutte le colonne della tabella author
    // i dati presenti nelle colonne non mappate vengono PERSI (sostituiti col default)
    // Quindi le foreign key le mappo con la colonna secca
    private Long authorId;
}
