package com.baemin.server.ceo.board.security;

import com.baemin.server.ceo.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvier {
    private static final Logger logger = LoggerFactory.getLogger( JwtTokenProvier.class);

    private static final String secretKey = "ZIGZAG";
    private static final long expireTime = 30; //(min)

    public String createToken( final User user ) throws UnsupportedEncodingException {

        Date now = new Date();
        Date expiration = new Date( Instant.now().toEpochMilli() + 1000 * 60 * expireTime );

        Claims claims = Jwts.claims().setSubject( user.getEmail() );

        Map<String, Integer> map = new HashMap<>();

        return Jwts.builder()
                .setHeaderParam( "type", "JWT" )
                .setClaims( claims )
                .setId( user.getEmail() )
                .setSubject( user.getEmail())
                .claim( "user", user )
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
