package com.example.library.app.configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import com.example.library.model.account.AccountService;
import com.example.library.model.role.RoleResource;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BiblioAuthUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final AccountService accountService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        var basicToken = token.getPrincipal().toString();
        if (!basicToken.startsWith("Basic ")) {
            log.info("Wrong token: {}", basicToken);
            throw new AuthenticationCredentialsNotFoundException("Basic");
        }
        var rest = basicToken.substring("Basic ".length());
        String subject;
        try {
            var bytes = Base64.getDecoder().decode(rest.getBytes(StandardCharsets.UTF_8));
            subject = new String(bytes, StandardCharsets.UTF_8);
        } catch (RuntimeException re) {
            log.info("Base64: {}", basicToken);
            throw new AuthenticationCredentialsNotFoundException("Basic", re);
        }
        if (!subject.endsWith(":password")) {
            log.info("Wrong Passowrd: {}", basicToken);
            throw new BadCredentialsException("Basic");
        }
        var username = subject.substring(0, subject.length() - ":password".length());
        var account = this.accountService.readAccountByUsername(username)
                .orElseThrow(() -> {
                    log.info("Username not found: {}", basicToken);
                    return new UsernameNotFoundException("Basic");});
        return new User(username, "N/A", account.getRoles().stream().map(RoleResource::getCode)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

}
