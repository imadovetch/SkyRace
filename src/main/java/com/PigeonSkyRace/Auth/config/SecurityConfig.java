package com.PigeonSkyRace.Auth.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    // Permit all access to the registration route
                    authorize.requestMatchers("/account/register").permitAll();

                    // Secure the competition creation endpoint
                    authorize.requestMatchers("/Api/Competition").hasRole("ORGANIZER");  // Securing the POST route with ROLE_ORGANIZER

                    // You can specify other endpoints or roles as needed
                    // Example:
                    // authorize.requestMatchers("/some-other-endpoint").hasRole("ADMIN");

                    // Default to authenticated users for other requests
                    authorize.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // Custom 401 handler
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            customAuthenticationEntryPoint.handleAccessDeniedException(request, response, (AccessDeniedException) accessDeniedException);
                        })
                )
                .authenticationProvider(new Special()) // Ensure you're using the correct authentication provider

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticatioginManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
