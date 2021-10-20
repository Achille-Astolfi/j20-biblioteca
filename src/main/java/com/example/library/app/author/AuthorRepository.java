package com.example.library.app.author;


import org.springframework.data.repository.CrudRepository;

// i repository estendono CrudRepository
// in tanti esempi online si trova l'annotation @Repository che, come già
// sappiamo, è ignorata da Spring; il package scan di Spring funziona
// perché cerca le interface che estendono CrudRepository, non perché
// trova l'annotation
public interface AuthorRepository extends CrudRepository<Author, Long> {
    // eventuali metodi funzionerebbero ancora, a patto di usare
    // SQL nativo al posto di JPQL/HQL
    // e a patto di usare named parameters al posto dei ?
}
