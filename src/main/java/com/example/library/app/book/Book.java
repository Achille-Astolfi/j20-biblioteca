package com.example.library.app.book;

import com.example.library.app.author.Author;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// le annotation di JPA che sarebbero le annotation di Hibernate
// Entity: è un entity javabean, rappresentazione di una entity di DB
@Entity
// Tabel: è l'annotation tecnica che stabilisce la relazione tra
// l'entity javabean e la tabella di DB
@Table(name = "book")
// le annotation di lombok
@Getter
// i setter servono quando dobbiamo creare un nuovo record
// in alternativa ai setter si possono usare (class Author)
@Setter
public class Book {

  //si usa un'unica colonna auto incrermentate che si annota con @Id
  @Id
  //per dire che è un autoincrementante si usa questa annotation:
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // L'annotation tecnica è Colum, stabilisce la relazione tra le property del
  // Javabean con la colonna del DB
  @Column(name = "book_id")
  private Long id;

  // per ogni colonna di Db che cìvogliamo rappresentare
  // nell'entity model Java uno un campo annotato colum
  // se la colonna di si chiama come il campo java
  // allora l'annotation colum si può ommettere a meno che la colonna DB non sia
  // dichiara NOT NULL, allora si è obbligati ad usare l'annotation Column
  @Column(name = "title", nullable = false)
  private String title;


  // le relazione di un modello entità relazioni le mappiamo con un campo che poi diventa property
  // il campo deve avere ocme tipo il tipo dell'entity o il tipo di List<entity>
  // esiste anche la possibilità di usare altre colection come Set<entity>
  // o Map<?, entity>
  // l'
  // un libro scritto da un autore, ma un autore può scrivere più di un libro
  // ManyToOne -> annotation che rappresenta questa relazione
  @ManyToOne
  // l'annotation tecnica è JoinCColum, si deve specificare il nome della colonna di questa entity
  // perchè la colonna di author è quella corrispondente al campo annotation con @id
  // nell'entity Author
  // se nullable è true non è obbligatorio perchè il suo default è true
  @JoinColumn(name = "author_id", nullable = true)
  private Author author;
}
