package com.baemin.server.ceo.service;

import com.sun.org.slf4j.internal.Logger;
import entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.slf4j.internal.LoggerFactory;


@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private static final String secretKey = "BAEMIN-SERVER";
    private static final long expireTime = 30; // (min)

    public String createToken( final UserEntity userEntity) throws UnsupportedEncodingException {

        Date now = new Date();
        Date expiration = new Date( Instant.now().toEpochMilli() + 1000 * 60 * expireTime );

        Claims claims = Jwts.claims().setSubject( userEntity.getId() );

        Map<String, Integer> map = new HashMap<>();

        return Jwts.builder()
                .setHeaderParam( "type", "JWT" )
                .setClaims( claims )
                .setId(userEntity.getId() )
                .setSubject( userEntity.getName())
                .claim( "user", userEntity)
                .setIssuedAt( now )
                .setExpiration( expiration )
                .signWith( SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                .compact();
    }

    public boolean verifyToken( final String jwt ) {
        try {
            Jwts.parser().setSigningKey( secretKey.getBytes("UTF-8") ).parseClaimsJws( jwt );
            return true;
        } catch ( Exception e ) {
            logger.error( "jwt is invalid - jwt: {}, error: {}", jwt, e.getMessage() );
        }

        return false;
    }
}
