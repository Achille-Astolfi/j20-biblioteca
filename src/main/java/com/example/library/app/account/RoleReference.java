package com.example.library.app.account;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

// è una "anomalia" nel mondo Object Relational Mapping
// perché questa class è la mappa di una tabella di relazione
// ripasso: le tabelle di relazione non sono entità per cui in JPA non hanno
// una class che le modella
// Chiamiamola "classe di appoggio" per modellare la relazione molti-molti tra
// Account e Role
// SE NON LA CHIAMO AccountRole devo usare per forza l'annotation @Table
@Table("account_role")
// L'annotation da mettere su RoleReference (sulle classi-di-relazione se vi piace il nome)
// è @Data perché Spring Data JDBC deve essere in grado di riconoscere gli oggetti-di-relazione
// (se vi piace il nome) dal valore del campo e non dal fatto che sono oggetti distinti
@Data
public class RoleReference {
    // nella class riferita non si dichiara l'id ma solo la colonna che va a riferire
    // l'altra tabella
    // I commenti proseguono nella class Account
    private Long roleId;
}
