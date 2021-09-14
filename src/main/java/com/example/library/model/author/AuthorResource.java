package com.example.library.model.author;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResource {
//tecnicamente questi sono campi di Java
  private Long id;
  private String firstName;
  private String lastName;
  //con le annotation di Lombok creo automaticamente le coppie get/set
  //Sono le coppie di metodi a definire le property di JavaBean
  //Per esempio posso aggiungere una property dichiarando due metodi in questo modo:
/*  public String getCavallo(){
    return firstName + lastName;
  }
  public void setCavallo(String cavallo){
    int i=cavallo.lastIndexOf(' ');
    setFirstName(cavallo.substring(0,i));
    setLastName(cavallo.substring(i+1));
  }*/
  //Potrei anche mettere le annotation su singoli fields che voglio che definiscano proprietà
  //invece di annotare tutta la classe

}
