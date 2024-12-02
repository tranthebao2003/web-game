package com.webgame.webgame.controller;

import com.webgame.webgame.model.User;
import com.webgame.webgame.sevice.user.UserService;
import dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String Register(@ModelAttribute("user") UserLoginDto userLoginDto) {
        return "login";
    }

    @PostMapping("/register")
    public String saveRegisterUser(@ModelAttribute("user") UserLoginDto userLoginDto, Model model) {
        if (userLoginDto.getRole() == null) {
            userLoginDto.setRole("user");
        }
        userService.save(userLoginDto);
        model.addAttribute("message","Tạo tài khoản thành công !");
        return "login";
    }
}
