package com.PigeonSkyRace.Auth.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomSpeciaAuth implements Authentication {

    private boolean authenticated;
    private String name;

    public CustomSpeciaAuth(boolean authenticated, String name) {
        this.authenticated = authenticated;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return an empty list of authorities (or provide roles if needed)
        return List.of();
    }

    @Override
    public Object getCredentials() {
        // In this case, you don't need credentials after authentication, so returning null
        return null;
    }

    @Override
    public Object getDetails() {
        // Optional: return additional details about the authentication request
        return null;
    }

    @Override
    public Object getPrincipal() {
        // Return the principal (the authenticated user’s name)
        return name;
    }

    @Override
    public boolean isAuthenticated() {
        // Return whether the user is authenticated
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        // Return the name of the authenticated user
        return this.name;
    }
}
