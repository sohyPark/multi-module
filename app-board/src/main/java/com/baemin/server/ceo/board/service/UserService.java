package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.dto.UserDto;
import com.baemin.server.ceo.board.enumtype.AuthCode;
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

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger( UserService.class );

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity signIn( final UserDto.addReq req) {

        final int auth = req.getAuth();
        if ( !AuthCode.isContains( auth ) ) {
            logger.error("auth is invalid");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "권한을 선택해주세요.");
        }

        final String name = req.getName();
        if (ObjectUtils.isEmpty(name)) {
            logger.error("name is required");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "이름을 입력해주세요.");
        }

        final String email = req.getEmail();
        if ( ObjectUtils.isEmpty(email)) {
            logger.error("email is required");
            return RestResponse.fail( HttpStatus.BAD_REQUEST, "이메일을 입력해주세요.");
        }

        final String password = req.getPassword();
        if (ObjectUtils.isEmpty(password)) {
            logger.error("password is required");
            return RestResponse.fail(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요.");
        }

        User user = User.builder()
                .name( name )
                .email( email )
                .password( password )
                .auth( auth )
                .build();

        User saveUser = userRepository.save( user );
        if (saveUser.getId() < 0){
            logger.error( "" );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "사용자 추가가 실패하였습니다." );
        }

        return RestResponse.success();
    }

    public ResponseEntity getUsers() {

        List<User> userList = userRepository.findAll();

        return RestResponse.success(userList);
    }
}
