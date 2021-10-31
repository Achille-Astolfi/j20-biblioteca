package com.example.library.app.account;

import com.example.library.app.role.Role;
import com.example.library.app.role.RoleMapper;
import com.example.library.app.role.RoleRepository;
import com.example.library.model.account.AccountResource;
import com.example.library.model.account.AccountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final RoleRepository roleRepository;
  private final AccountMapper accountMapper;
  private final RoleMapper roleMapper;

  @Override
  @Transactional(readOnly = true)
  public Optional<AccountResource> readAccountByUsername(String username){
    // mi prendo un Account conoscendo lo username

    // spring data JDBC mi popola in automatico il set di RoleReferenced
    // devo recuperare gli oggetti role corrispondenti ai RoleReference
    // gli oggetti Role li converto in RoleResource
    // l'oggetto Account lo converto in AccountResource
    Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
    // monto tutto e restituisco
//    return optionalAccount.map(this.accountMapper::toResource);
    return optionalAccount.map(this::toResourceImpl);
  }

  private AccountResource toResourceImpl(Account entity){

    List<Role> roleList = new ArrayList<>();
    for(RoleReference reference : entity.getRoleReferences()){
      roleList.add(this.toRoleEntityImpl(reference));
    }
    AccountResource resource = this.accountMapper.toResource(entity);
    resource.setRoles(this.roleMapper.toResourceList(roleList));
    return resource;
  }
  @NonNull
  private Role toRoleEntityImpl(@NonNull RoleReference roleReference){
    Optional<Role> role = this.roleRepository.findById(roleReference.getRoleId());
    if(role.isEmpty()){
      throw new RuntimeException();
    }
    return role.orElseThrow();
  }

}
