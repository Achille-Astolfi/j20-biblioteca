package com.example.library.app.author;

import org.springframework.data.repository.CrudRepository;

//i repository estendono crud repository
// in tanti esempio online si trova l'annotation @Repository che è ignorata da spring
public interface AuthorRepository extends CrudRepository<Author, Long> {
  // eventuali metodi funzionerebber ancora apatto di usare SQL nativo al posto di JPSL/HQL
  // e a patto di usarenamed parameters al posto dei ?

}
