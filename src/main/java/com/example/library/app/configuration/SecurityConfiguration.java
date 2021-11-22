package com.example.library.app.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    @Order(0)
    public SecurityFilterChain securityFilterChainSwagger(HttpSecurity http) throws Exception {
        return common(http).requestMatchers()
                // actuator (ma sarà da securizzare)
                .antMatchers("/actuator/**", "/favicon.ico")
                // eventuali endpoint senza autenticazione
                .antMatchers("/error")
                // la login e la logout
                .antMatchers("/login", "/logout").and()
                // tutte le risorse in questo gruppo passano senza auth
                .authorizeRequests().anyRequest().permitAll().and()
                // build
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainAuthorize(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return common(http).requestMatchers()
                // tutte le altre path (vedi order 0 e order 1)
                .anyRequest().and()
                // tutte le risorse in questo gruppo passano con auth
                .authorizeRequests().anyRequest().authenticated().and()
				.addFilterAfter(accessTokenFilter(authenticationManager), ExceptionTranslationFilter.class)
                // no anonymous
                .anonymous().disable()
                // no session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
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

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>  authOrchestratorService) {
		return new ProviderManager(preauthAuthProvider(authOrchestratorService));
	}

	private AbstractPreAuthenticatedProcessingFilter accessTokenFilter(AuthenticationManager authenticationManager) {
		var bean = new RequestHeaderAuthenticationFilter();
        bean.setPrincipalRequestHeader("Authorization");
		bean.setAuthenticationManager(authenticationManager);
		return bean;
	}

	private PreAuthenticatedAuthenticationProvider preauthAuthProvider(
			AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authUserDetailsService) {
		var preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
		preauthAuthProvider.setPreAuthenticatedUserDetailsService(authUserDetailsService);
		return preauthAuthProvider;
	}
}
