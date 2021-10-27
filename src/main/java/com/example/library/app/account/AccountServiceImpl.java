package com.example.library.app.account;

import com.example.library.app.role.Role;
import com.example.library.app.role.RoleMapper;
import com.example.library.app.role.RoleRepository;
import com.example.library.model.account.AccountResource;
import com.example.library.model.account.AccountService;
import com.example.library.model.role.RoleResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountMapper accountMapper;
  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;
  private final AccountRepository accountRepository;

  @Override
  @Transactional(readOnly = true)
  public Optional<AccountResource> readAccountByUsername(String username) {
    //mi prendo un Account con username
    Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
    //l'oggetto Account lo converto in AccountResource
    return optionalAccount.map(this::toAccountResourceImpl);
  }

  private AccountResource toAccountResourceImpl(Account entity) {
    //Spring Data JDBC popola in automatico il set di Rolereference
    //Devo recuperare gli oggetti Role corrispondenti a Rolereference
    //gli oggetti Role li converto in Rolereference
    List<Role> roleList = new ArrayList<>();
/*    for(RoleReference roleReference : entity.getRoleReferences()) {
      roleList.add(this.toRoleEntityImpl(roleReference));
    }*/
    roleList = entity.getRoleReferences().stream().map(this::toRoleEntityImpl)
        .collect(Collectors.toList());
    AccountResource resource = this.accountMapper.toResource(entity);
    resource.setRoles(this.roleMapper.toResourceList(roleList));
    return resource;
  }

  private Role toRoleEntityImpl(RoleReference roleReference){
    Optional<Role> role = this.roleRepository.findById(roleReference.getRoleId());
    if (role.isEmpty()) {
      throw new RuntimeException();
    }
    return role.orElseThrow();
  }
}
