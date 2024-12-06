package com.webgame.webgame.service.cart;

import com.webgame.webgame.model.Cart;
import com.webgame.webgame.model.CartGame;
import com.webgame.webgame.repository.CartGameRepository;
import com.webgame.webgame.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartGameRepository cartGameRepository;

    public Cart getCartByUserId(Long userId) {
        createCartByUserId(userId);
        // Lấy giỏ hàng từ cơ sở dữ liệu
        Cart cart = cartRepository.findCartByUserId(userId).orElse(null);

        if (cart != null) {
            // Tính tổng giá của giỏ hàng
            BigDecimal totalPrice = cartGameRepository.calculateTotalPriceByCartId(cart.getCartId());
            // Cập nhật tổng giá vào giỏ hàng (nếu cần thiết)
            cart.setSumPrice(totalPrice.intValue()); // Giả sử bạn lưu tổng giá dưới dạng int
        }
        return cart; // Trả về giỏ hàng cùng tổng giá
    }


    public Cart createCartByUserId(Long userId) {
        // Kiểm tra xem người dùng đã có giỏ hàng chưa
        Optional<Cart> existingCart = cartRepository.findCartByUserId(userId);

        if (existingCart.isPresent()) {
            // Nếu giỏ hàng đã tồn tại, không làm gì và trả về giỏ hàng hiện tại
            return existingCart.get();
        }
        // Nếu chưa có giỏ hàng, tạo giỏ hàng mới
        Cart cart = new Cart();
        cart.setCartId(userId); // Gán cartId bằng userId
        cart.setSumPrice(0); // Khởi tạo sumPrice = 0 nếu cần thiết

        // Lưu giỏ hàng vào cơ sở dữ liệu
        return cartRepository.save(cart); // Lưu và trả về giỏ hàng mới tạo
    }


    public CartGame addGameToCart(Long userId, Long gameId){
        createCartByUserId(userId);
        CartGame cartGame = new CartGame();
        cartGame.setCartId(userId); // Gán cartId từ userId
        cartGame.setGameId(gameId); // Gán gameId
        return cartGameRepository.save(cartGame);
    }
}


