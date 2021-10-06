package com.example.library.app.author;

import com.example.library.app.book.Book;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
// in alternativa ai Setter potrei usare Builder
@Builder
// se uso Builder devo aggiungere le annotation NoArgsContructor e AllArgsConstructor
// perché NoArgsConstructor serve a Hibernate e AllArgsConstructor serve al Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // annotation non obbligatoria
    @Column(name = "first_name")
    private String firstName;

    // la comodità di avere un campo List<Book> nella class Author si scontra (spesso) con
    // rischi connessi a "strane/inaspettate eccezioni" e "performance limitate"
    // Niente di grave, si possono sistemare, ma c'è da chiedersi: vale davvero la pena?
    // l'annotation "gemella" di ManyToOne è OneToMany
    // l'annotation "gemella" di JoinColumn non c'è, è un attributo di OneToMany: mappedBy
    // il valore dell'attributo è il nome del campo (non della colonna di DB)
//    @OneToMany(mappedBy = "author")
//    private List<Book> books;
}
