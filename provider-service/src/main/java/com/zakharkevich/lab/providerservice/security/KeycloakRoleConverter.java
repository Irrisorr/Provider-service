package com.zakharkevich.lab.providerservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        var roles = (List<String>) jwt.getClaimAsMap("realms_access").get("roles");

        return Stream.concat(authorities.stream(),
                        roles.stream()
                                .filter(role -> role.startsWith("ROLE_"))
                                .map(SimpleGrantedAuthority::new)
                                .map(GrantedAuthority.class::cast))
                .toList();
    }
}
