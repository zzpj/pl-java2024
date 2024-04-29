package com.example.demo.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get-token")
public class TokenProviderController {
    private final JwtEncoder jwtEncoder;

    @PostMapping
    public String generateToken(Authentication auth) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(120);

        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));      // get user authorities from the authentication header and join them

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("me")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(auth.getName())        // add the user a token should be issued to
                .claim("scope", scope)      // add user roles
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();    // generate token based on provided data
    }
}
