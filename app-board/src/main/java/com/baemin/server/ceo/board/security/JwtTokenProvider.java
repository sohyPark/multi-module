package com.baemin.server.ceo.board.security;

import com.baemin.server.ceo.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger( JwtTokenProvider.class);

    private String secretKey = "baemin-server";

    private static final long expireTime = 30; //(min)

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken( final User user ) throws UnsupportedEncodingException {

        Date now = new Date();
        Date expiration = new Date( Instant.now().toEpochMilli() + 1000 * 60 * expireTime );

        Claims claims = Jwts.claims().setSubject( user.getEmail() );
        claims.put( "role", user.getRole() );

        return Jwts.builder()
                .setHeaderParam( "type", "JWT" )
                .setClaims( claims )
                .setId( user.getEmail() )
                .setSubject( user.getEmail() )
                .claim( "user", user )
                .setIssuedAt( now )
                .setExpiration( expiration )
                .signWith( SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication( String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken( HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
