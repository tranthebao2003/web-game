package com.webgame.webgame.controller;

import com.webgame.webgame.model.Cart;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @GetMapping("/cart/{userId}")
    public String viewCart(@PathVariable("userId") Long userId, Model model) {

        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        return "test"; // trả về tên view (cart.html)
    }
}
