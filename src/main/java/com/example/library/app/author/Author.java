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



@Getter
@Setter
// in alternativa ai setter si possono usare i Builder
@Builder
// se si usa il builder si devono aggiungere le annotaion NoArgsConstructor e AllArgsConstructor
// perchè NoArgsConstructor serve a Hibernate e AllArgsContructor serve al Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "author_id")
  public Long id;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  //la comodità di avere un campo List<book> nelal class Author is scontra con
  // rischi di strane/inaspettate eccezioni e scarse perfomance limitate
  //annotation per uno a molti
//@OneToMany(mappedBy = "author")
  // l'annotation gella di JoinColum non esuste ma esiste un attributi di OneToMany, : mappedBy
  // il valore dell'attributo è il nome del campo (non della colonna di DB)
//private List<Book> books;
}
