package com.example.library.model.account;

import com.example.library.app.LibraryBoot;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryBoot.class)
@ActiveProfiles("jupiter")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    @Sql({"/role.sql", "/account.sql", "/readAccountByUsernameTest.sql"})
    void readAccountByUsernameTest() {
        Optional<AccountResource> optionalResource = this.accountService.readAccountByUsername("user");
        assertTrue(optionalResource.isPresent());
        assertFalse(optionalResource.get().getRoles().isEmpty());
    }
}