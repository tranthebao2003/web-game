package com.webgame.webgame.controller;

import com.webgame.webgame.model.Cart;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.service.user.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
//    @GetMapping("")
//    public String cart() {
//        return "cart";
//    }

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String cart(Model model) {
        List<Game> games = cartService.getAllGames();
        model.addAttribute("game", games);
        return "cart"; // Trả về trang cart.html
    }

//    @GetMapping("")
//    public String getUserCart(@RequestParam("userId") Long userId, Model model) {
//        List<Cart> carts = cartService.getGamesInCartByUserId(userId);
//        model.addAttribute("carts", carts);
//        return "cart";
//    }

}
