package com.webgame.webgame.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.webgame.webgame.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByRole(String role);
    User findByEmail(String email);

    List<User> findAll();

    List<User> findListByRole(String role);

}
