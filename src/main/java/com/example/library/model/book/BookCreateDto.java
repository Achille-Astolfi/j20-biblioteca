package com.example.library.model.book;

import com.example.library.model.author.AuthorCreateDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
  //situazione semplificata
  private String title;
  //id dell'autore viene passato grazie al menu a tendina del frontend
  private Long authorId;
  //situazione più realistica: o mi passa l'authorId oppure addirittura un authorCreateDto
  private AuthorCreateDTO author;
  //ovviamente in questo caso un sistema di validazione deve impedire che vengano
  //speficicati authorId e anche authorCreateDto
  //la dipendenza tra BookCreateDto e AuthorCreateDto è ammissibile perché
  //ci troviamo all'interno del contesto del model
}
