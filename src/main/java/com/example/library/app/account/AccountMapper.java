package com.example.library.app.account;

import com.example.library.model.account.AccountResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", source = "accountId")
    AccountResource toResource(Account entity);
}
