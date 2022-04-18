package com.vicras.abaclib.use.security.service;

import static java.util.Objects.isNull;

import com.vicras.abaclib.use.model.CommonUser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;


@Component
public class JWTService {

    private static final String USER_LOGIN = "user_login";

    @Value("${token.expire.minutes}")
    private int tokenMinutesAlive;

    @Value("${token.secret}")
    private String key;

    public String getToken(CommonUser user) {

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim(USER_LOGIN, user.getLogin());
        jwtBuilder.setIssuer(String.valueOf(user.getId()));
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + tokenMinutesAlive * 60000L));
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
    }

    private void validateToken(String token) {
        DefaultClaims claims;

        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }

        if (isNull(claims.getExpiration())) {
            throw new AuthenticationServiceException("Invalid token");
        }

        Date expiredDate = claims.getExpiration();
        if (!expiredDate.after(new Date())) {
            throw new AuthenticationServiceException("Token expired date error");
        }

    }

    public Long getUserIdIfTokenValid(String token) {
        validateToken(token);
        var claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        return Long.parseLong(claims.getIssuer());
    }
}