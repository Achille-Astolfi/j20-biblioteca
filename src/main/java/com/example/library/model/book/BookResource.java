package com.example.library.model.book;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResource {
    private Long id;
    private String title;
    private String author;

    // con le annotation lombok si generano automaticamente le coppie
    //  get/set sono le coppie di metodi a deginire la property di javabean
    // per esempio posso aggiungere una property dichiarando due metodi in questo modo:
    //    public String getcavallo(){
    //        return title + " " + author;
    //    }
    //
    //    public void setcavallo (String cavallo){
    //        int i = cavallo.lastIndexOf(' ');
    //        setTitle(cavallo.substring(0, i));
    //        setAuthor(cavallo.substring(i + 1));
    //    }



}
