package com.example.library.app.book;

import com.example.library.app.author.Author;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// le annotation di JPA
// che sarebbero le annotation di Hibernate
// Entity: è un entity JavaBean, rappresentazione di una entity di DB
@Entity
// Table: è l'annotation tecnica che stabilisce la relazione tra l'entity JavaBean
// e la tabella di DB
@Table(name = "book")
// e per finire le annotation di lombok
@Getter
// i setter ci servono "solo" quando dobbiamo creare un nuovo record; quindi in
// alternativa a Setter possiamo usare (vedi class Author)
@Setter
public class Book {
    // oggi dopo decenni di esperienza con DB relazionali abbiamo capito che è meglio NON usare
    // chiavi composte da più colonne ma si usa un'unica colonna autoincrementante.
    // che si annota con @Id
    @Id
    // per dire che è un autoincrementante si usa questa annotation:
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // l'annotation tecnica è Column, stabilisce la relazione tra la properti del JavaBean
    // con la colonna di DB
    @Column(name = "book_id")
    private Long id;

    // per ogni colonna di DB che vogliamo rappresentare nell'entity model Java
    // uso un campo annotato con Column
    // Per i più pigri: se la colonna di DB si chiama come il campo Java, allora
    // l'annotation Column si può omettere; a meno che la colonna di DB non sia
    // dichiarata NOT NULL, allora è obbligatorio da usare
    @Column(name = "title", nullable = false)
    private String title;

    // le relazioni di un Modello Entità Relazioni le mappiamo con un campo (che poi diventa
    // property)
    // Il campo deve avere come tipo: il tipo dell'entity o il tipo List<entity>
    // Esiste anche la possibilità di usare altre collection come Set<entity> o addirittura
    // Map<?, entity> dove la chiave è un "discriminator" a nostro piacimento, non necessariamente
    // l'@Id
    // Situazione semplificata: un libro scritto da un autore, ma un autore può scrivere
    // più di un libro. L'annotation che rappresenta questa relazione è ManyToOne
    @ManyToOne
    // L'annotation tecnica è JoinColumn; devo specificare il nome della colonna di QUESTA entity
    // perché la colonna "dell'altra parte" è quella corrispondente al campo annotato con Id
    // nell'entity riferita ossia Author
    // Giusto per essere esplicito, dico che l'author non è obbligatorio, ma nullable = true
    // è il default e può essere omesso.
    @JoinColumn(name = "author_id", nullable = true)
    private Author author;
}
