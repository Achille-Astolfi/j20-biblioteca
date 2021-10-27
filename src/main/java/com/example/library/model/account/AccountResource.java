package com.example.library.model.account;

import com.example.library.model.role.RoleResource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountResource {
    private Long id;
    private String username;
    private String fullName;

    private List<RoleResource> roles;
}
