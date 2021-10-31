package com.example.library.app.book;

import org.springframework.data.repository.CrudRepository;

// non si annota l'interface, in particolare con repository
// (non è un errore l'annotation è ignorata, viene considerata solo una class, non su interface)
public interface BookRepository extends CrudRepository<Book,Long> {

}
