package com.example.library.app.account;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

//E' un'anomalia del mondo Object Relational Mapping
//perché questa class è la mappa di una tabella di relazione
//ripasso: le tabelle di relazione non sono entità per cui in JPA non hanno
//una class modello; La chiamiamo infatti "class di appoggio" per modellare
//la relazione molti-molti tra Account e Role che invece sono entità
//Ma devo trattarla come un'entità perché se non la chiamo come AccountRole
// devo usare per forza l'annotation table
@Table("account_role")
//L'annotation da mettere sulle classi di relazione è Lombok Data
//Perché Spring Data JDBC deve essere in grado di riconoscere che due istanze
//di oggetti di relazione dal valore del campo roleId e non dal fatto che sono
//oggetti distinti: die oggetti RoleReference che hanno lo stesso valore di
//roleId devono essere riconosciuti come equals
@Data
public class RoleReference {
  //nella class riferita non si dichiara l'id ma solo la colonna
  //che va a riferire l'altra tabella
  //I commenti proseguono nella classe Account
  private Long roleId;

}
