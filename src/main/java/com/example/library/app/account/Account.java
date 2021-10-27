package com.example.library.app.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

@Getter
@Setter
public class Account {
    @Id
    private Long accountId;
    private String username;
    private String fullName;

    // nella class account dichiaro un java.util.Set di RoleReference
    // e lo annoto con @MappedCollection
    @MappedCollection(idColumn = "account_id")
    private Set<RoleReference> roleReferences;

}
