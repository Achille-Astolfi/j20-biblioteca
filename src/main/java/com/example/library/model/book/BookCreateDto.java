package com.example.library.model.book;

import com.example.library.model.author.AuthorCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
    // situazione semplificata
    private String title;
    // l'id dell'autore mi viene passato grazie al menù a tendina presente nel front-end
    private Long authorId;
    // situazione più complessa
    // ovviamente uno strumento di validazione deve impedire che vengano specificate insieme
    // authorId e author; questo è un esempio, la dipendenza tra BookCreateDto e AuthorCreateDto
    // è ammissibile perché ci troviamo nel contesto del model
    private AuthorCreateDto author;
}
