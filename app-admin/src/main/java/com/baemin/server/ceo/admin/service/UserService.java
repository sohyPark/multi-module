package com.baemin.server.ceo.admin.service;

import com.baemin.server.ceo.admin.dto.UserDto;
import com.baemin.server.ceo.admin.enumtype.ActiveStatus;
import com.baemin.server.ceo.core.code.Role;
import com.baemin.server.ceo.core.entity.User;
import com.baemin.server.ceo.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger( UserService.class );

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity signIn( final UserDto.addReq req ) {

        final int role = req.getRole();
        if ( !Role.isContains( role ) ) {
            logger.error( "role is invalid" );
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode( password );

        User user = User.builder()
                .name( name )
                .email( email )
                .password( encodePassword )
                .role( role )
                .build();

        User saveUser = null;
        try {
            saveUser = userRepository.save( user );
        } catch ( DataIntegrityViolationException e ) {
            logger.error( "email already exist - email: {}, error: {}", email, e.getMessage() );
            return new ResponseEntity<>( "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST );
        }

        if ( saveUser.getId() < 0) {
            logger.error( "user save is failed - role: {}, name: {}, email: {}", role, name, email );
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
        user.setRole( req.getRole() );
        user.setName( req.getName() );
        user.setPassword( req.getPassword() );

        User updatedUser = userRepository.save( user );
        if ( updatedUser.getId() < 1 ) {
            logger.error( "user update is failed - id: {}, role: {} name: {}", id, req.getRole(), req.getName() );
            return new ResponseEntity<>( "사용자 수정이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity hide( long id ) {

        Optional<User> findUser = userRepository.findById( id );
        if ( !findUser.isPresent() ) {
            logger.error( "not found user by id - id: {}", id );
            return new ResponseEntity<>( "사용자를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        User user = findUser.get();
        user.setActive( ActiveStatus.IN_ACTIVE.ordinal() );

        User updatedUser = userRepository.save( user );
        if ( updatedUser.getId() < 1 ) {
            logger.error( "user hide is failed - : id: {}", id );
            return new ResponseEntity<>( "게시판 숨김이 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public ResponseEntity get() {
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>( userList, HttpStatus.OK );
    }
}
