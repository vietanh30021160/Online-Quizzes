package com.swp.online_quizz.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swp.online_quizz.Entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findByRole(String role);

    Optional<User> findByUsername(String username);

    @Query("select u.email from User u where u.email = ?1")
    String findByEmail(String email);

    List<User> findByRoleAndIsActive(String role, Boolean isActive);

    List<User> findByUsernameIgnoreCaseContainingAndRole(String username, String role);

    List<User> findByIsActive(Boolean isActive);

    User findUserByEmail(String email);
}
