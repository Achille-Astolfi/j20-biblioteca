package com.example.library.model.book;

import com.example.library.model.author.AuthorCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
  // situazioni semplificata
  private String title;
  // l'id dell'autore viene passato tramite il frontend
  private Long authorId;
  // situazione più complessa: passa l'autore nome e cognome come oggetto json
  // con uno strumento di validazione che deve impedire che vengano specificate insieme
  // authorid e author: es la dipendenza tra BookCreateDto e Author  CreateDto
  // è ammissibile perch ci troviamo nel contesto model
  private AuthorCreateDto author;

}
