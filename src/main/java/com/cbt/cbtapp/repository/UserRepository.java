package com.cbt.cbtapp.repository;

import com.cbt.cbtapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}
