package com.example.library.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.library.app.LibraryBoot;
import com.example.library.model.author.AuthorResource;
import com.example.library.model.author.AuthorService;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@Sql({"/role.sql","/account.sql","/readAccountByUsernameTest.sql"})
public class AccountServiceTest {
  @Autowired
  private AccountService accountService;

  @Test
  void readAccountByUsername() {
    String user = "user";
    Optional<AccountResource> output = this.accountService.readAccountByUsername(user);
    assertTrue(output.isPresent());
  }
}
