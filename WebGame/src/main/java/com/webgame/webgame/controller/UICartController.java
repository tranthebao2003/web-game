package com.webgame.webgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class UICartController {
    @GetMapping("")
    public String cartUi(Model model) {
        return "cart";
    }

    @GetMapping("/pay")
    public String payUi(Model model) {
        return "pay";
    }
}
