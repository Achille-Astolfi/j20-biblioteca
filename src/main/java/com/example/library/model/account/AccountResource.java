package com.example.library.model.account;

import com.example.library.model.role.RoleResource;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResource {
  private Long id;
  private String username;
  private String fullName;

  private List<RoleResource> roles;

}
