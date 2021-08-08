package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.security.JwtTokenProvider;
import com.baemin.server.ceo.board.util.RestResponse;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger( LoginService.class );

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity login( final User user, final HttpServletResponse response) {

        final String email = user.getEmail();
        if ( ObjectUtils.isEmpty(email)) {
            logger.error("email is required");
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "이메일을 입력햊세요.");
        }

        final String password = user.getPassword();
        if (ObjectUtils.isEmpty(password)) {
            logger.error("password is required");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "비밀번를 입력햊세요.");
        }

        Optional<User> findUser;
        try {
            findUser = userRepository.findUserByEmailAndPassword(email, password);
        } catch (Exception e) {
            logger.error("User findUserByIdAndPassword error : {}, email: {}", e.getMessage(), email);
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        if ( !findUser.isPresent() ) {
            logger.error("User not found - email: {}", email);
            return ResponseEntity.status( HttpStatus.NO_CONTENT ).body( "사용자를 찾을 수 없습니다. " );
            //return RestResponse.fail(HttpStatus.NO_CONTENT, "사용자를 찾을 수 없습니다.");
        }

        String token = null;
        try {
            token = jwtTokenProvider.createToken(user);
        } catch (Exception e) {
            System.out.println();
            logger.error("create token - user: {}, error: {}", user, e.getMessage());
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        if (token == null) {
            logger.error("created token is null - email: {}", email);
            return RestResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 생성이 실패하였습니다.");
        }

        response.setHeader("jwt-auth-token", token);

        return RestResponse.success();
    }

    public ResponseEntity logout( long id ) {
        return RestResponse.success(id);
    }

}
