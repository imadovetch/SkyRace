package com.PigeonSkyRace.Auth.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    // Method to decode the JWT token and extract the BreederId
    public String getBreederIdFromToken(String authorizationHeader) {
        try {

            // Extract the token from the "Authorization" header
            String token = authorizationHeader.replace("Bearer ", "");
            // Initialize the JwtDecoder with the secret key
            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256")).build();

            // Decode the token
            Jwt decodedJwt = jwtDecoder.decode(token);



            // Extract the "subject" claim, which should be the breeder's ID
            String breederId = decodedJwt.getSubject(); // This corresponds to breeder.getId()

            // Print the breeder ID for debugging
            System.out.println("Breeder ID from token: " + breederId);

            return breederId;
        } catch (Exception ex) {
            System.out.println("Error decoding token: " + ex.getMessage());
            return null;
        }
    }


}

