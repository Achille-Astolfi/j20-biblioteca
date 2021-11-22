package com.example.library.model.book;

import lombok.Getter;
import lombok.Setter;

// @Setter di sicuro, altrimenti non ho un punto d'ingresso per salvare i valori
@Setter
// @Getter dipende dalla configurazione della libreria Jackson
@Getter
public class BookResource {
    // tecnicamente questi sono campi (fields) Java
    private Long id;
    private String title;
    private String author;
}
