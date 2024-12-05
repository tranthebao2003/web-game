package com.webgame.webgame.repository;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Cart;
import com.webgame.webgame.model.CartGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
