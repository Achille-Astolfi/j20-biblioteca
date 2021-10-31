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

  // nella class account dichiaro un Java.util.Set di rolereference
  @MappedCollection(idColumn = "account_id")
  private Set<RoleReference> roleReferences;

}
