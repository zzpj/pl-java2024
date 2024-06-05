package com.zzpj.TrainTripUsersAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.AbstractUserRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SpringBootApplication
public class TrainTripUsersAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTripUsersAdapterApplication.class, args);
    }

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8999")
                .realm("train-trips-ecosystem")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username("admin")
                .password("admin")
                .build();
    }

    @Bean
    CommandLineRunner commandLineRunner(Keycloak keycloak) {
        return args -> {

            //http://localhost:8999/realms/train-trips-ecosystem
            List<UserRepresentation> userRepresentations = keycloak.realm("train-trips-ecosystem")
                    .users()
                    .search("user", true);
            List<String> usernames = userRepresentations.stream().map(AbstractUserRepresentation::getUsername).toList();
            return;
        };
    }
}

@EnableWebSecurity
class SecurityConfig {

    @Bean
    KeycloakLogoutHandler keycloakLogoutHandler() {
        return new KeycloakLogoutHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth
                -> auth.requestMatchers("/external")
                .permitAll()
                .anyRequest()
                .authenticated()
        );
        http.oauth2Login(Customizer.withDefaults());
        http.logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler()).logoutUrl("/external"));
        return http.build();
    }
}

@RestController
class UserController {

    @GetMapping("/internal")
    public String getSecretInfo() {
        return "secret info, visible after login...";
    }

    @GetMapping("/external")
    public String getExternalInfo() {
        return "content visible for all, no login required...";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUrl;

    @GetMapping("/profile")
    public void profile(HttpServletResponse response)  {
        response.setHeader("Location", issuerUrl + "/account");
        response.setStatus(302);
    }
}

@Component
class KeycloakLogoutHandler implements LogoutHandler {

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUrl;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String logout = issuerUrl + "/protocol/openid-connect/logout";

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(logout)
                .queryParam("id_token_hint", ((OidcUser) authentication.getPrincipal()).getIdToken().getTokenValue());

        ResponseEntity<String> logoutEntity = new RestTemplateBuilder()
                .build()
                .getForEntity(uriComponentsBuilder.toUriString(), String.class);

        if (!logoutEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Logout failed: " + logoutEntity.getBody());
        }
    }
}