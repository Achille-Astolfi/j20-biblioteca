package com.example.library.app.account;

// è una "anomalia" nel mondo Object Relational Mapping
// perchè questa class è la mappa di una tabella di relazione
// le tabella di relazione non sono entità, ma sono il modello di una relazione a molti a molti
// questa può essere chiamata "Classe di appoggio" per modella la relezione molti a molti
// tra Account e role
// se non la si chiama AccountRole, bisogna usare per forza l'annotation @Table

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("account_role")
// l'annotation da mettere su RoleReference (sulle classi di relazione se vi piace il nome) è @Data
// perchè SpringData JDBC deve essere in grado di riconoscere gli oggetti di relazione
// dal valore del campo e non dal fatto che sono ggetti distinti

@Data
public class RoleReference {
  // nella classe riferita non si dichiara l'id ma
  // solo la colonna che a riferire l'altra tabella
  private Long roleId;

}
