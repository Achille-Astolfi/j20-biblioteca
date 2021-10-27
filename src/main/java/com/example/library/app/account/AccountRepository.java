package com.example.library.app.account;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
  //Abbiamo bisogno di dichiarare un metodo che esegue una query
  //lo posso fare in due modi: usando esplicitamente una annotation oppure
  //usando implicitamente il nome del metodo
  //La convenzione è che i metodi iniziano per find, a un certo punto hanno By
  //segue la descrizione del filtro da applicare
  //(NB i nomi dei campi li scrivo come nella entity)
  //Se mi aspetto un solo valore allora metto Optional<Accont>
  //Argomento chiamato come il campo per convenzione
  Optional<Account> findByUsername(String username);

  //questo metodo fa la stessa cosa di findByUsername
  @Query("SELECT * FROM \"account\" WHERE \"username\"=:username")
  Optional<Account> metodoConUnNomeInutile(String username);

  //Se mi aspetto più di un risultato uso List
  List<Account> findAccountsByFullName(String fullName);

}
