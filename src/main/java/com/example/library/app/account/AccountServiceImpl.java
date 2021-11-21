package com.example.library.app.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.library.app.role.Role;
import com.example.library.app.role.RoleMapper;
import com.example.library.app.role.RoleRepository;
import com.example.library.model.account.AccountResource;
import com.example.library.model.account.AccountService;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// questo service potrebbe essere un buon candidato per implementare anche UserDetailsService
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResource> readAccountByUsername(String username) {
        // mi prendo un Account conoscendo lo username
        Optional<Account> optionalAccount = this.accountRepository.findByUsername(username);
        // ho spostato la logica in toResourceImpl
        return optionalAccount.map(this::toResourceImpl);
    }

    private AccountResource toResourceImpl(Account entity) {
        AccountResource resource = this.accountMapper.toResource(entity);
        // Spring Data JDBC mi popola in automatico il Set di RoleReference
        // devo recuperare gli oggetti Role corrispondenti ai RoleReference
        // Set<RoleReference> -> List<Role>
        List<Role> roleList = new ArrayList<>();
        for (RoleReference reference : entity.getRoleReferences()) {
            roleList.add(this.toRoleEntityImpl(reference));
        }
        // VARIANTE con Stream e Method reference:
//        roleList = entity.getRoleReferences().stream().map(this::toRoleEntityImpl)
//                .collect(Collectors.toList());
        // gli oggetti Role li converto in RoleResource
        // List<Role> -> List<RoleResource>
        resource.setRoles(this.roleMapper.toResourceList(roleList));
        // monto tutto e restituisco
        return resource;
    }

    @NonNull
    private Role toRoleEntityImpl(@NonNull RoleReference reference) {
        // se il DB non è corrotto, mi aspetto che esista sempre un record corrispondente
        Optional<Role> optionalRole = this.roleRepository.findById(reference.getRoleId());
        if (optionalRole.isEmpty()) {
            // qualcosa di meglio da sollevare?
            throw new RuntimeException();
        }
        return optionalRole.get();
    }
}
