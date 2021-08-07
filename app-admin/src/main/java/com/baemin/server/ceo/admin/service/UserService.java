package com.baemin.server.ceo.admin.service;

import com.baemin.server.ceo.admin.dto.UserDto;
import com.baemin.server.ceo.admin.enumtype.AuthCode;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger( UserService.class );

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity signIn( final UserDto.addReq req ) {

        final int auth = req.getAuth();
        if ( !AuthCode.isContains( auth ) ) {
            logger.error( "auth is invalid" );
            return new ResponseEntity<>( "권한을 선택해주세요.", HttpStatus.BAD_REQUEST );
        }

        final String name = req.getName();
        if ( ObjectUtils.isEmpty( name ) ) {
            logger.error( "name is required" );
            return new ResponseEntity<>( "이름을 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        final String email = req.getEmail();
        if ( ObjectUtils.isEmpty( email ) ) {
            logger.error( "email is required" );
            return new ResponseEntity<>( "이메일을 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        final String password = req.getPassword();
        if ( ObjectUtils.isEmpty( password ) ) {
            logger.error( "password is required" );
            return new ResponseEntity<>( "비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST );
        }

        User user = User.builder()
                .name( name )
                .email( email )
                .password( password )
                .auth( auth )
                .build();

        User saveUser = userRepository.save( user );
        if ( saveUser.getId() < 0 ) {
            logger.error( "" );
            return new ResponseEntity<>( "사용자 추가가 실패하였습니다.", HttpStatus.BAD_REQUEST );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity update( final long id, final UserDto.updateReq req ) {

        Optional<User> findUser = userRepository.findById( id );
        if ( !findUser.isPresent() ) {
            logger.error( "not found user by id - id: {}", id );
            return new ResponseEntity<>( "사용자를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        User user = findUser.get();
        user.setAuth( req.getAuth() );
        user.setName( req.getName() );
        user.setPassword( req.getPassword() );

        User updatedUser = userRepository.save( user );
        if ( updatedUser.getId() < 1 ) {
            logger.error( "user update is failed - id: {}, auth: {} name: {}", id, req.getAuth(), req.getName() );
            return new ResponseEntity<>( "사용자 수정이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }
}
