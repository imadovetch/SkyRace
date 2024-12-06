package com.PigeonSkyRace.Auth.Config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class Special implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Retrieve name and password from the Authentication object
        String name = authentication.getName();
        String pass = (String) authentication.getCredentials();

        // Implement the special authentication logic
        if ("specialName".equals(name) && "specialPass".equals(pass)) {
            // Return authenticated CustomSpeciaAuth object if credentials are valid
            return new CustomSpeciaAuth(true, name);
        }

        // If authentication fails, return null (Spring Security handles this appropriately)
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Ensure this provider supports the CustomSpeciaAuth authentication type
        return CustomSpeciaAuth.class.isAssignableFrom(authentication);
    }
}
