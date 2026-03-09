package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.TokenDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.InvalidTokenException;
import com.example.userservice.exceptions.PasswordMismatchException;
import com.example.userservice.models.Token;
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
    public String login(@RequestBody LoginRequestDto requestDto) throws PasswordMismatchException {
        return userService.login(requestDto.getEmail(), requestDto.getPassword());
//        Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());

//        return TokenDto.from(token);
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) throws InvalidTokenException {
        System.out.println("Validating token!");
        User user = userService.validateToken(tokenValue);

        return UserDto.from(user);
    }

    @GetMapping("/sample")
    public void sampleAPI() {
        System.out.println("Received a call from ProductService!");
    }

    //TODO
//    public logOut() {
//
//    }
}