package com.webgame.webgame.controller;

import com.webgame.webgame.dto.UserDto;
import com.webgame.webgame.dto.UserLoginDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.model.User;
import com.webgame.webgame.service.accountGame.AccountGameService;
import com.webgame.webgame.service.category.CategoryService;
import com.webgame.webgame.service.game.GameService;
import com.webgame.webgame.service.user.UserService;
import com.webgame.webgame.service.user.UserServiceImp;
import com.webgame.webgame.service.userLogin.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/userInfo")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;


    //show duoc thong tin người dùng
//    @GetMapping("/user_info")
//    public String getUser(Model model,
//                          RedirectAttributes redirectAttributes) {
//        // Lấy đối tượng User hiện tại từ SecurityContextHolder
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = userDetails.getUsername(); // Giả sử email là username
//
//        // Lấy thông tin chi tiết của người dùng từ cơ sở dữ liệu bằng email
//        User user = userService.getUserByEmail(email);
//        System.out.println("Thông tin người dùng từ database: " + user);
//        if (user == null) {
//            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
//        }
//
//        // Đưa thông tin người dùng vào model
//        model.addAttribute("userInfo", user);
//
//        return "user"; // Tên template Thymeleaf
//    }
//
//    @PostMapping("/update_user")
//    public String updateUserInfo(@ModelAttribute("userInfo") UserDto userDto,
//                                 RedirectAttributes redirectAttributes) {
//
//        try {
//            // Lấy email của người dùng hiện tại từ SecurityContextHolder
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String email = userDetails.getUsername();
//
//            // Gọi service để cập nhật thông tin người dùng
//            userService.updateUser(email, userDto);
//
//            // Thêm thông báo thành công
//            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
//        } catch (Exception e) {
//            // Thêm thông báo lỗi
//            redirectAttributes.addFlashAttribute("error", "Cập nhật thông tin thất bại: " + e.getMessage());
//        }
//
//        // Chuyển hướng về trang thông tin người dùng
//        return "redirect:/user_info";
//    }
    //hết phần show

    @GetMapping("/userInfo")
    public String showFormUpdateUser(Model model, RedirectAttributes redirectAttributes) {
        try {
            // Lấy thông tin người dùng từ Spring Security
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // Lấy thông tin người dùng từ cơ sở dữ liệu bằng email
            User userExist = userService.getUserByEmail(email);

            if (userExist == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin người dùng!");
                return "redirect:/register_login"; // Hoặc một trang khác phù hợp
            }

            // Chuyển đổi User thành UserDto để dễ xử lý trong form
            UserDto userDto = new UserDto();
            userDto.setUsername(userExist.getFullName());
            userDto.setEmail(userExist.getEmail());
            userDto.setPhone(userExist.getPhone());

            // Đưa thông tin vào model
            model.addAttribute("userInfo", userDto);
            model.addAttribute("userExist", userExist);

            return "user"; // Tên template Thymeleaf
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            return "redirect:/register_login"; // Hoặc một trang khác phù hợp
        }
    }


    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute("userInfo") UserDto userDto,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Lấy thông tin người dùng từ Spring Security
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // Lấy người dùng hiện tại từ cơ sở dữ liệu
            User userExist = userService.getUserByEmail(email);

            if (userExist == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng để cập nhật!");
                return "redirect:/register_login"; // Hoặc một trang khác phù hợp
            }

            // Gọi service để cập nhật thông tin người dùng
            userService.updateUser(userExist.getUserId(), userDto);

            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            // Thêm thông báo lỗi
            redirectAttributes.addFlashAttribute("error", "Cập nhật thông tin thất bại: " + e.getMessage());
        }

        // Chuyển hướng về trang thông tin người dùng
        return "redirect:/userInfo";
    }

    //Don mua
    @GetMapping("/purchasedGames")
    public String getPurchasedGames(Model model, RedirectAttributes redirectAttributes) {
        try {
            // Lấy email người dùng từ Spring Security
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // Gọi service để lấy danh sách game
            List<Game> games = gameService.getGamesByUser(email);

            // Đưa danh sách game vào model để hiển thị trên giao diện
            model.addAttribute("games", games);

            return "user"; // Tên template Thymeleaf để hiển thị danh sách game
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lấy danh sách game: " + e.getMessage());
            return "redirect:/error"; // Chuyển hướng đến trang lỗi
        }
    }

}
