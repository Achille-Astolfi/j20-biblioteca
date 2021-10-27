package com.example.library.app.account;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    // ho bisogno di dichiarare un metodo che esegue una determinata query
    // lo posso fare in due modi: usando esplicitamente una annotation oppure
    // usando implicitamente il nome del metodo
    // La convenzione è che i metodi inziano per find, a un certo punto hanno By
    // segue la descrizione del filtro da applicare (NOTA BENE: i nomi dei campi
    // li scrivo come sono scritti nella entity)
    // find-By-Username: cerco per il nome del CAMPO username
    // se mi aspetto un solo valore, restituisco Optional dell'entity
    // Per ogni campo nominato nel nome del metodo uso un parametro; il parametro
    // posso chiamarlo "pippo", ma se lo chiamo come il campo seguo la convenzione
    Optional<Account> findByUsername(String username);

    // questo  metodo fa la stessa cosa di findByUsername
    @Query("SELECT * FROM \"account\" WHERE \"username\"=:username")
    // :username diventa ? quando viene tradotta la query in SQL per PreparedStatement.
    // DEVE esserci corrispondenza tra :username nella query e il nome del parametro
    // del metodo; se scrivo :cavallo il parametro deve chiamarsi cavallo
    Optional<Account> metodoConUnNomeInutile(String username);

    // tra find e By si può scrivere quello che si vuole
    // per esempio findAccountByUsername
    // findAccountsByFullName
    // Se mi aspetto più di un risultato uso List
    List<Account> findAccountsByFullName(String fullName);
}
