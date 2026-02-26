package com.example.userservice.repositories;

import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String tokenValue);

    Token save(Token token);

    //validate the token.
    //Check if the token is present in the table with the given value.
    //now check if expiry of the token > current time.
    //select * from tokens where token_value = ? and expiry_at > ?
    Optional<Token> findByTokenValueAndExpiryAtGreaterThan(
            String tokenValue,
            Date expiryAt);

}