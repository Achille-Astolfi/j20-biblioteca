package com.example.library.app.account;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

@Getter
@Setter
public class Account {
  @Id
  private Long accountId;
  private String username;
  private String fullName;

  //Nella classe Account dichiaro un java.util.Set di RoleReference
  @MappedCollection(idColumn = "account_id")
  private Set<RoleReference> roleReferences;

}
