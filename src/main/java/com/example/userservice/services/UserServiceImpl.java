package com.example.userservice.services;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.userservice.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signUp(String name, String email, String password) {
        //Check if there's already a user with the email or not.
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            //redirect to the login page.
            return optionalUser.get();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // User BCryptPasswordEncoder to encode the password.
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public User validateToken(String tokenValue) {
        return null;
    }
}