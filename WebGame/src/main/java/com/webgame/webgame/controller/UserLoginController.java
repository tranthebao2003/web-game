package com.webgame.webgame.controller;

import com.webgame.webgame.service.userLogin.UserLoginService;
import com.webgame.webgame.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {



    @Autowired
    private UserLoginService userLoginService;

    @GetMapping("/register_login")
    public String Register(@ModelAttribute("user") UserLoginDto userLoginDto) {
        return "login";
    }

    @PostMapping("/register_login")
    public String saveRegisterUser(@ModelAttribute("user") UserLoginDto userLoginDto, Model model) {
        if (userLoginDto.getRole() == null) {
            userLoginDto.setRole("user");
        }
        userLoginService.save(userLoginDto);
        model.addAttribute("message","Tạo tài khoản thành công !");
        return "login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("user")
    public String userPage(){
        return "home";
    }

    @GetMapping("admin_page")
    public String adminPage(){
        return "admin";
    }

}
