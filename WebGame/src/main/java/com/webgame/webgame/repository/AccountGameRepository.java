package com.webgame.webgame.repository;

import com.webgame.webgame.model.AccountGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountGameRepository extends JpaRepository<AccountGame, Long> {
}
