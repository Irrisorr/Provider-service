package com.zakharkevich.lab.providerservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.zakharkevich.lab.providerservice.security.SecurityConstants.*;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        List<String> roles = (List<String>) jwt.getClaimAsMap(REALMS_ACCESS_CLAIM).get(ROLES_CLAIM_NAME);

        return Stream.concat(authorities.stream(),
                        roles.stream()
                                .filter(role -> role.startsWith(ROLE_PREFIX))
                                .map(SimpleGrantedAuthority::new)
                                .map(GrantedAuthority.class::cast))
                .toList();
    }
}
