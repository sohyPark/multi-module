package com.baemin.server.ceo.core.repository;

import com.baemin.server.ceo.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByIdAndPassword( String id, String password);
}
