package com.PigeonSkyRace.Auth.Entity.User;


import com.PigeonSkyRace.Auth.Entity.Role.RoleResponseDto;
import com.PigeonSkyRace.Auth.Entity.model.PigeonResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String userID;
    private String username;
    private String nomColombie;
    private Set<RoleResponseDto> roles;

    private List<PigeonResponseDto> pigeons;
}
