package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.TokenDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.User;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto requestDto) {
        return null;
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) {
        return null;
    }

    //TODO
//    public logOut() {
//
//    }
}