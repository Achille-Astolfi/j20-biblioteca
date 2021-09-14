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
    // con le annotation di Lombok genero automaticamente le coppie
    // get/set; sono le coppie di metodi a definire le property di JavaBean
    // per esempio posso aggiungere una property dichiarando due metodi in questo modo:
    public String getCavallo() {
        return title + " " + author;
    }

    public void setCavallo(String cavallo) {
        int i = cavallo.lastIndexOf(' ');
        setTitle(cavallo.substring(0, i));
        setAuthor(cavallo.substring(i + 1));
    }
}
