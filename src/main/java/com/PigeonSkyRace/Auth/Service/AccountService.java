package com.PigeonSkyRace.Auth.Service;


import com.PigeonSkyRace.Auth.Entity.MapStruct.UserMapper;
import com.PigeonSkyRace.Auth.Entity.Role.Role;
import com.PigeonSkyRace.Auth.Entity.User.Breeder;
import com.PigeonSkyRace.Auth.Entity.User.UserDto;
import com.PigeonSkyRace.Auth.Entity.User.UserResponseDto;
import com.PigeonSkyRace.Auth.Exception.EntityAlreadyExistsException;
import com.PigeonSkyRace.Auth.Repository.BreederRepository;
import com.PigeonSkyRace.Auth.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class AccountService {

    private final BreederRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;



    public ResponseEntity<Object> InfoAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<Breeder> otherUser = Optional.ofNullable(userRepository.findByNomColombie(authentication.getName()));

        if (otherUser.isPresent()) {
            return generateUserResponseDto(otherUser.get());
        }

        return new ResponseEntity<>("Utilisateur non trouvé", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> createUser(UserDto userDto){
        var bCryptEncoder = new BCryptPasswordEncoder();

        Breeder appuser = userMapper.userDtoToAppUser(userDto);



        Optional<Breeder> otherUser = Optional.ofNullable(userRepository.findByNomColombie(userDto.getNomColombie()));

        if (otherUser.isPresent()) {
            throw new EntityAlreadyExistsException("NomColombie", "NomColombie  already used.");
        }

        Role ROLE_USER = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found!"));

        appuser.setUserID(generateUserID(appuser));
        appuser.setPassword(bCryptEncoder.encode(userDto.getPassword()));

        appuser.setRoles(Set.of(ROLE_USER));
    

        userRepository.save(appuser);

        return generateUserResponseDto(appuser);
    }

    private ResponseEntity<Object> generateUserResponseDto(Breeder breeder) {
        // Generate JWT token
     //   String jwtToken = securityConfiguration.createJwtToken(appUser);

        // Map AppUser to UserResponseDto
        UserResponseDto userResponseDto = userMapper.appUserToUserResponseDto(breeder);

        // Prepare response
        var response = new HashMap<String, Object>();
     //   response.put("token", jwtToken);
        response.put("user", userResponseDto);

        return ResponseEntity.ok(response);
    }

    private String generateUserID(Breeder breeder) {
        return UUID.randomUUID().toString().substring(0, 10) + breeder.getNomColombie().toLowerCase()  + UUID.randomUUID().toString().substring(0, 10);
    }





    //    public ResponseEntity<Object> Login(LoginDto loginDto){
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
//        );
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // You can access the roles here
//        for (GrantedAuthority authority : authorities) {
//            System.out.println("Role: " + authority.getAuthority());
//        }
//
//        AppUser user = userRepository.findByEmail(loginDto.getEmail())
//                .orElseThrow(() -> new EntityNotFoundException("User", "User not found with email: " + loginDto.getEmail()));
//        return generateUserResponseDto(user);
//
//    }

}
