package com.webgame.webgame.controller;

import com.webgame.webgame.service.thanhtoan.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BuyController {

    @Autowired
    private BuyService buyService;

    @GetMapping("/buynow")
    public String buyNow(Model model) {
        Long gameId = 3L; // Giá trị ví dụ, bạn có thể lấy từ request parameter hoặc session
        Long userId = 26L; // Giá trị ví dụ, bạn có thể lấy từ request parameter hoặc session

//        try {
//            // Gọi phương thức buyNow từ BuyService và nhận phản hồi
//            String result = buyService.buyNow(gameId, userId);
//
//            // Thêm thông báo vào model để hiển thị trên view
//            model.addAttribute("message", result);
//
//            // Trả về tên view để hiển thị kết quả
//            return "test"; // Tên view thành công hoặc lỗi
//        } catch (ResponseStatusException e) {
//            // Xử lý lỗi từ ResponseStatusException và thêm thông báo lỗi vào model
//            model.addAttribute("message", "Lỗi: " + e.getReason());
//            return "test"; // Tên view lỗi
//        } catch (Exception e) {
//            // Xử lý các lỗi không mong muốn khác và thêm thông báo lỗi vào model
//            model.addAttribute("message", "Đã xảy ra lỗi không mong muốn: " + e.getMessage());
//            return "test"; // Tên view lỗi
//        }


          String result=  buyService.buyInCart(userId);
          model.addAttribute("result",result);
        return "test";
    }
}
