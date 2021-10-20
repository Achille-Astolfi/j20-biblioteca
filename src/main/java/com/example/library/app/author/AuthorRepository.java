package com.example.library.app.author;


import org.springframework.data.repository.CrudRepository;

//i repository estendono CrudRepository
//in tanti esempi online si trova l'annotation @Repository che è ignorata da Spring
//questo perché Spring riconosce l'interfaccia Repository SOLO perché estende CrudRepository, non per l'annotation
public interface AuthorRepository extends CrudRepository<Author, Long> {
//eventuali metodi funzionerebbero ancora a patto di utilizzare
  //SQL nativo al posto di JPQL/HQL e a patto di usare named parameters al posto di ?
}
