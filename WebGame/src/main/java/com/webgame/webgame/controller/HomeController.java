package com.webgame.webgame.controller;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.sevice.accountGame.AccountGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    AccountGameService accountGameService;

    @GetMapping("")
    public String home(Model model) {
        List<AccountGame>  accountGames = accountGameService.getAllAccountGames();
        model.addAttribute("accountGames", accountGames);
        return "home";
    }
}
