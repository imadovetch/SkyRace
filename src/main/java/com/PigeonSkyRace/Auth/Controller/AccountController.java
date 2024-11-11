package com.PigeonSkyRace.Auth.Controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.PigeonSkyRace.Auth.models.Breeder;
import com.PigeonSkyRace.Auth.models.LoginDto;
import com.PigeonSkyRace.Auth.models.RegisterDto;
import com.PigeonSkyRace.Auth.models.ResponseRegisterDto;
import com.PigeonSkyRace.Auth.repository.BreederRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Api/account")
public class AccountController {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;


    @Autowired
    BreederRepository breederRepository;

    @Autowired
    ResponseRegisterDto responseRegisterDto ;


    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("Username", auth.getName());
        response.put("Authorities", auth.getAuthorities());
        List<Breeder> breeders = breederRepository.findByUsername(auth.getName());
        // Check for results
        if (breeders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else if (breeders.size() > 1) {
            // Handle multiple results, e.g., return first or some error response
            response.put("User", breeders.get(0)); // You can choose how to handle this
        } else {
            response.put("User", breeders.get(0)); // Exactly one result
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult result) {

        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();
            for (var error : errorsList) {
                var fieldError = (FieldError) error;
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }

        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getNomColombie(), loginDto.getPassword())
            );

            // Find the user by nomColombie
            System.out.println("Attempting to log in with nomColombie: " + loginDto.getNomColombie());

            Breeder user = breederRepository.findByNomColombie(loginDto.getNomColombie());


            if (user == null) {
                return ResponseEntity.status(401).body("Invalid username or password");
            }

            BeanUtils.copyProperties(user , responseRegisterDto);
            // Generate a JWT token
            String jwtToken = createJwtToken(user);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", responseRegisterDto);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed: " + ex.getMessage()); // Log the error
            return ResponseEntity.status(401).body("Invalid nomColombie or password");
        } catch (Exception ex) {
            System.out.println("There is an Exception: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterDto registerDto,
            BindingResult result) {

        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (var error : errorsList) {
                var fieldError = (FieldError) error;
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }

        var bCryptEncoder = new BCryptPasswordEncoder();
        Breeder breeder = new Breeder();
        breeder.setUsername(registerDto.getUsername());
        breeder.setNomColombie(registerDto.getNomColombie());
        breeder.setRole("Breeder");
        breeder.setLatitude(registerDto.getLatitude());
        breeder.setLongitude(registerDto.getLongitude());
        breeder.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

        try {
            // Check if username/email are already used
            var NomColombie = breederRepository.findByNomColombie(registerDto.getNomColombie());
            if (NomColombie != null) {
                return ResponseEntity.badRequest().body("NomColombie already used");
            }


            // Save the new user
            breederRepository.save(breeder);

            // Generate JWT token
            String jwtToken = createJwtToken(breeder);

            BeanUtils.copyProperties(breeder , responseRegisterDto);
            // Prepare response
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", responseRegisterDto);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }
            return ResponseEntity.status(500).body("Internal Server Error");
    }




    private String createJwtToken(Breeder breeder) {
        // Get the current time
        Instant now = Instant.now();

        // Build the JWT Claims Set
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer) // Set the issuer (for example, your app's name or domain)
                .issuedAt(now) // Time when the token was issued
                .expiresAt(now.plusSeconds(24 * 3600)) // 1 day expiration
                .subject(breeder.getId()) // The subject of the token, typically the user ID
                .claim("role", breeder.getRole()) // Add the role as a claim
                .claim("nomColombie", breeder.getNomColombie()) // You can add other custom claims if necessary
                .claim("latitude", breeder.getLatitude()) // Custom claims for extra information
                .claim("longitude", breeder.getLongitude())
                .build();

        // Initialize the JwtEncoder with the secret key for HMAC
        var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));

        // Create the JWT encoding parameters
        var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        // Encode the JWT and return the token value
        return encoder.encode(params).getTokenValue();
    }
}
