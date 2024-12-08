package com.webgame.webgame.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.webgame.webgame.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByRole(String role);
    User findByEmail(String email);
}
