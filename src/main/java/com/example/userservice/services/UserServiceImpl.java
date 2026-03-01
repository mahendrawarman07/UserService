package com.example.userservice.services;

import com.example.userservice.exceptions.InvalidTokenException;
import com.example.userservice.exceptions.PasswordMismatchException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.userservice.repositories.UserRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           SecretKey secretKey) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
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
    public String login(String email, String password) throws PasswordMismatchException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            //redirect the user to the signUp page.
            return null;
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //Login unsuccessful.
            throw new PasswordMismatchException("Incorrect password.");
        }

        //Login successful.
        //Generate the Token.
//        Token token = new Token();
//        token.setUser(user);
//
//        /// random alphanumeric string of 128 characters.
//        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, 30);
//        Date expiryDate = calendar.getTime();
//        token.setExpiryAt(expiryDate);
//
//        return tokenRepository.save(token);

        //Generate a JWT Token using JJWT library.

        //Let's not hardcode the payload, instead create a payload from the user details.
//        String payload = "{\n" +
//                "  \"email\": \"Mahendrawarman@gmail.com\",\n" +
//                "  \"userId\": \"2\",\n" +
//                "  \"roles\": [\"STUDENT\"],\n" +
//                "  \"expiry\": \"2026-04-05T12:34:56Z\"\n" +
//                "}";

//        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "productservice.com");
        claims.put("userId", user.getId());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDate = calendar.getTime();

//        String token = Jwts.builder().content(payloadBytes).compact();

//        return token;

        claims.put("exp", expiryDate);
        claims.put("roles", user.getRoles());

//        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = macAlgorithm.key().build();

//        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
//        String token = Jwts.builder().content(payloadBytes).compact();

        String jwtToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        //Create a UserSession as well here in order to store more details with the token.

        return jwtToken;

    }

    @Override

    public User validateToken(String tokenValue) throws InvalidTokenException {
//        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(
//                tokenValue,
//                new Date());
//
//        if (optionalToken.isEmpty()) {
//            //Invalid token
//            throw new InvalidTokenException("Invalid token.");
//        }
//
//        //Token is valid.
//        Token token = optionalToken.get();
//        return token.getUser();

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(tokenValue).getPayload();

        System.out.println("claims "+claims);

        //        Date expiryDate = (Date) claims.get("exp");
//        Date currentDate = new Date();

        Long expiryTime = (Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();
        // here we are comparing expiry time and current time in milliseconds, but the expiry time is in seconds, so we need to convert it to milliseconds before comparing.
        expiryTime = expiryTime * 1000; // this will convert the expiry time from seconds to milliseconds.

        if (expiryTime < currentTime) {
            //Token is InValid.

            //TODO - Check expiry Time and current time (Milliseconds vs Seconds) issue. // done
            System.out.println("Expiry time : " + expiryTime);
            System.out.println("Current time : " + currentTime);

            throw new InvalidTokenException("Invalid JWT token.");
        }
            //Token is Valid.
            Long userId = Long.valueOf((Integer) claims.get("userId"));
            Optional<User> optionalUser = userRepository.findById(userId);

            System.out.println(userId+" "+optionalUser);
            return optionalUser.get();
    }
}


/// Secret Key - in the vault / AWS secret manager