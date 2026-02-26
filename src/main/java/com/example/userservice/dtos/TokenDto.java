package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenDto {
    private String tokenValue;
    private Date expiryAt;
    private String email;

    public static TokenDto from(Token token) {
        if (token == null) return null;

        TokenDto tokenDto = new TokenDto();
        tokenDto.setTokenValue(token.getTokenValue());
        tokenDto.setExpiryAt(token.getExpiryAt());
        tokenDto.setEmail(token.getUser().getEmail());

        return tokenDto;
    }
}