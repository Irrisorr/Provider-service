package com.zakharkevich.lab.providerservice.security;

public final class SecurityConstants {
    public static final String REALMS_ACCESS_CLAIM = "realms_access";
    public static final String ROLES_CLAIM_NAME = "roles";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String PROVIDER_READ_AUTHORITY = "hasAuthority('provider.read')";
    public static final String PROVIDER_WRITE_AUTHORITY = "hasAuthority('provider.write')";

    private SecurityConstants() {}
}