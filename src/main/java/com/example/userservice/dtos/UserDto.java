package com.example.userservice.dtos;
import lombok.Getter;
import lombok.Setter;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUserId(user.getId());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}