package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("select u.email from User u where u.email = ?1")
    String findEmailByEmail(String email);

    @Query("select u from User u where u.email = ?1")
    User findUserByEmail(String email);

    Optional<User> findByEmail(String username);
}
