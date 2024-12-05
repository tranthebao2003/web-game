package com.webgame.webgame.repository;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.CartGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartGameRepository extends JpaRepository<CartGame, Long> {
}
