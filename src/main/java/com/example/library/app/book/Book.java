package com.example.library.app.book;

import com.example.library.app.author.Author;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//annotation di Lombok
@Getter
@Setter
//le annotation di JPA
//che sarebbero le annotation di Hibernate
//dichiarare che questo è un entity JavaBean, cioè la rappresentazione di un entity di un DB:
@Entity //annotazione di Java persistence
//dichiarare la relazione tra l'entity JavaBean e la tabella del DB
@Table(name = "book")
public class Book {
  //opo anni di utilizzo dei DB relazionali abbiamo capito che è meglio non usare chiavi composte
  //da più colonne ma si usa un'unica colonna autoincrementante
  //che quindi si annota con @Id
  @Id
  //annotation che dice che la colonna non va impostata esplicitamente ma viene calcolata
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //l'annotation tecnica è Column, stabilisce la relazione tra la property del JavaBean
  //e la colonna del DB
  @Column(name = "book_id")
  public Long id;

  //per ogni colonna di DB che vogliamo rappresentare nell'entity model Java
  //uso un campo annotato con Column
  //per i più pigri: se la colonna di DB si chiama come il campo Java, allora
  //l'annotation Column si può omettere; a meno che la colonna di DB non sia
  //dichiarata NOT NULL, allora è obbligatorio da usare
  @Column(name = "title", nullable = false)
  private String title;

  //Voglio che faccia riferimento all'entity author che è stata creata nel pacchetto author
  //le relazioni di un Modello Entita Relazioni le mappiamo con un campo (che poi diventa
  //property)
  //Il campo deve avere come tipo: il tipo dell'entity o il tipo List<Entity>
  //Esiste anche la possibilità di usare altre collection come Set<Entity> o addirittura
  //Map<?, entity> dove la chiave è un "discriminator" a nostro piacimento,
  //non necessariamente l'@Id
  //ci mettiamo nella situazione sempplificata in cui a ogni libro corrisponde un autore
  //e un autore può corrispondere a più libri: l'annotation che rappresenta questa relazione è ManyToOne
  @ManyToOne
  //L'annotation tecnica è JoinColumn; devo specificare il nome della colonna di QUESTA entity
  //perché il nome della colonna dall'altra parte è la colonna annotata con Id dell'entity author
  @JoinColumn(name = "author_id", nullable = true)
  private Author author;
}
