package com.webgame.webgame.controller;

import com.webgame.webgame.model.User;
import com.webgame.webgame.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping("")
//    public String getUser( Model model) {
//        Long userId = 25L;
//        User user = userService.getUserById(userId);
//        model.addAttribute("user", user);
//        return "user";
//    }
}
