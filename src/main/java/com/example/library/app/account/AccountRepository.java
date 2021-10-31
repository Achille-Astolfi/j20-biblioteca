package com.example.library.app.account;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
  // ho bisogno di dichiarare un metodo che sesegue una determinata query
  // lo posso fare in due modi: usando esplicitamente una annotation oppure
  // usando implicitamente il nome del metodo
  // la convenzione è che i metodi iniziano per find,
  // i nomi dei campo li scrivo come nella entity

  // per ogni campo nominato nel nome del metodo uso un paramentro,
  // il parametro posso chiamarlo pippo, ma se lo chiamo come il campo seguo la convenzione

  Optional<Account> findByUsername(String username);

  // questo metodo fa la stessa cosa di findByUsername
  @Query("SELECT * FROM \"account\" WHERE \"userbame\"=:username")
  // :username diventa ? quando viene tradotta la query in SQL per PreparedStatement
  // deve esserci corrispondenza tra :username nella query e il nome del paramentro del metodo
  // se scrivo :cavallo il parametro deve chiamarsi cavallo
//  Optional<Account> metodoConNomeItule(String username);

  List<Account> findAccountsByFullName(String fullName);

}
