package com.tutorial.finaldemo.reponsitory;

import com.tutorial.finaldemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, Integer> {
//    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findByVerifiedFalseAndRegistrationTimeBefore(LocalDateTime time);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

}
