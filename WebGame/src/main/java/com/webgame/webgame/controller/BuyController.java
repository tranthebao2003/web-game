package com.webgame.webgame.controller;
import com.webgame.webgame.service.thanhtoan.BuyService;
import com.webgame.webgame.service.userLogin.CustomUserLoginDetail;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class BuyController {

    @Autowired
    private BuyService buyService;

    @GetMapping("/xacnhandonhang")
    public String xacnhanbuyincart(@RequestParam(value = "selectedGames", required = false) List<Long> selectedGames, Model model, HttpSession session){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
        if (isLoggedIn) {
        Object principal = authentication.getPrincipal();
        CustomUserLoginDetail userDetails = (CustomUserLoginDetail) principal;
        Long userId= userDetails.getId();
        session.setAttribute("selectedGames", selectedGames);
        System.out.println(selectedGames);
//        [1, 3]

        Map<String, Object> result = buyService.xacNhanDonHang(selectedGames);
        model.addAttribute("result", result);
        return "thanhtoan/pay";}

        else return "redirect:/register_login";
    }

    @GetMapping("/thanhtoan")
    public String buyNow( Model model, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        CustomUserLoginDetail userDetails = (CustomUserLoginDetail) principal;
        Long userId= userDetails.getId();

        List<Long> selectedGames = (List<Long>) session.getAttribute("selectedGames");
        System.out.println("cai nay o cai thanh toan" +selectedGames);
        String result = buyService.buyInCart(userId, selectedGames);
        model.addAttribute("message", result);
        return "redirect:/userInfo";
    }

    @GetMapping("/huythanhtoan")
    public String cancelPayment(HttpSession session) {
        // Remove selectedGames from session
        session.removeAttribute("selectedGames");
        return "redirect:/cart";
    }
}
