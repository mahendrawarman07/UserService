package com.example.userservice.services;

import com.example.userservice.exceptions.InvalidTokenException;
import com.example.userservice.exceptions.PasswordMismatchException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;

public interface UserService {
    User signUp(String name, String email, String password);

    String login(String email, String password) throws PasswordMismatchException;;

    User validateToken(String tokenValue) throws InvalidTokenException;
}