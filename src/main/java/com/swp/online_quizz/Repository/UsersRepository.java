package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT email FROM User WHERE LOWER(email) = LOWER(?1)")
    String findEmailByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String username);
    List<User> findByRoleAndIsActive(String role, Boolean isActive);
    List<User> findByRole(String role);
    List<User> findByUsernameIgnoreCaseContainingAndRole(String username, String role);
    List<User> findByIsActive(Boolean isActive);
    User findUserByEmail(String email);
    User findByuserId(Integer userId);
}
