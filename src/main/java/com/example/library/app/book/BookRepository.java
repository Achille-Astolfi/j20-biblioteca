package com.example.library.app.book;

import org.springframework.data.jpa.repository.JpaRepository;

//In Spring Data JPA è molto facile trovate i due tipi generici:
//a) il tipo dell'entity è la class annotata con @Entity
//b) il tipo dell'ID è il tipo del campo annotato con @Id
//NON si annota l'interface, in particolare non si annota con @Repository
//Non è un errore, semplicemente l'annotation è ignorata perché non è su una class
//Spring Data scrive automaticamente una "class di nome BookRepositoryImpl"*
//che implementa BookRepository e che è annotata con @Reposiitory
//*In realtà la class che implementa BookRepository non ha nome ma il bean di Spring
//(il component ossia l'oggetto) ha come nome bookRepositoryImpl"
public interface BookRepository extends JpaRepository<Book, Long> {

}
