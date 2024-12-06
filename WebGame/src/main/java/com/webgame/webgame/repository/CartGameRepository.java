package com.webgame.webgame.repository;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.CartGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface CartGameRepository extends JpaRepository<CartGame, Long> {

//    boolean existsByCartIdAndGameId(Long cartId, Long gameId);
//
//    // Truy vấn tính tổng tiền cho một Cart dựa trên giá của game trong giỏ hàng
//    @Query("SELECT SUM(c.game.price) FROM CartGame c WHERE c.cart.cartId = :cartId")
//    BigDecimal calculateTotalPriceByCartId(Long cartId);
}
