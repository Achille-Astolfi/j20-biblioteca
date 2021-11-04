package com.example.library.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain securityFilterChainSwagger(HttpSecurity http) throws Exception {
        return common(http).requestMatchers()
                // actuator (ma sarà da securizzare)
                .antMatchers("/actuator/**", "/favicon.ico")
                // la login e la logout
                .antMatchers("/login", "/logout").and()
                .formLogin().failureHandler(new AuthenticationEntryPointFailureHandler(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value())).and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)).and()
                // tutte le risorse in questo gruppo passano senza auth
                .authorizeRequests().anyRequest().permitAll().and()
                // build
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainAuthorize(HttpSecurity http) throws Exception {
        return common(http).requestMatchers()
                // tutte le altre path (vedi order 0 e order 1)
                .anyRequest().and()
                // tutte le risorse in questo gruppo passano con auth
                .authorizeRequests().anyRequest().authenticated().and()
                // no anonymous
                .anonymous().disable()
                // build
                .build();
    }

    private HttpSecurity common(HttpSecurity http) throws Exception {
        // operazioni comuni a tutte le SecurityFilterChain
        return http

                // cors MVC
                .cors(withDefaults())
                // no csrf
                .csrf().disable()
                // AuthenticationException deve essere gestita come 401 mentre
                // AccessDeniedException deve essere gestita come 403
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl()).and();
    }

}
