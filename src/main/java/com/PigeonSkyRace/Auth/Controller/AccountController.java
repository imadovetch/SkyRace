package com.PigeonSkyRace.Auth.Controller;


import com.PigeonSkyRace.Auth.Entity.User.UserDto;
import com.PigeonSkyRace.Auth.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/current-user")
    public ResponseEntity<Object> getCurrentUser() {
        return accountService.InfoAuth() ;
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody UserDto userDto
           ) {
        return accountService.createUser(userDto) ;
    }




//    @PostMapping("/login")
//    public ResponseEntity<Object> login(
//            @Valid @RequestBody LoginDto loginDto
//           ) {
//        return accountService.Login(loginDto) ;
//    }
}
