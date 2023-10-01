package com.bridgelabz.book.store.app.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import static java.security.KeyRep.Type.SECRET;

@Component

public class JWTToken {

    private static final String TOKEN_SECRET = "Tanuja";

    /**
     * To create a token
     * @param id
     * @return
     */
    public String createToken(Long id){
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        String token = JWT.create().withClaim("user_Id", id).sign(algorithm);
        return token;
    }
    public static long decodeToken(String token) {
        long id = 0;
        if (token != null) {
            id = JWT.require(Algorithm.HMAC256(String.valueOf(SECRET))).
                    build().verify(token).
                    getClaim("id").asLong();
        }
        return id;
    }

    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(String.valueOf(SECRET))).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
