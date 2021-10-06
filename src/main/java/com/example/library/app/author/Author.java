package com.example.library.app.author;

import com.example.library.app.book.Book;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author")
@Getter
@Setter
//In alternativa a setter si può usare builder
@Builder
//se uso builder devo aggiungere:
@NoArgsConstructor
@AllArgsConstructor
//perché se non metto nessuna delle due mi fa come se avessi messo allargsconstructor
//ma in questo modo non ho noargsconstructor che serve a hibernate
//non metto data perché due entry devono essere considerate uguali solo
//se hanno id uguale, no confronto su altri campi
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "author_id")
  public Long id;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  //la comodità di avere un campo List<Book> qui si scontra poi con rischi connessi a
  //"strane/inaspettate eccezioni" e scarse "performance limitate"
  //Si possono sistemare ma vale davvero la pena?
  //l'annotation gemella di JoinColumn non c'è, è l'attributo di OneToMany mappedBy
  //il valore dell'attributo è il nome del campo, non della colonna del DB
  //@OneToMany(mappedBy = "author") //annotation gemella di ManyToOne
  //private List<Book> bookList;

}
