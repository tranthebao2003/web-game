package com.webgame.webgame.controller;

import com.webgame.webgame.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping("")
    public String cart(Model model) {

        return "cart"; // Trả về trang cart.html
    }
}
