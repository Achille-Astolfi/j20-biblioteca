package com.example.library.model.account;

import java.util.Optional;

public interface AccountService {
  Optional<AccountResource> readAccountByUsername(String username);

}
