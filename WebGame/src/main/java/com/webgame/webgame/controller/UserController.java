package com.webgame.webgame.controller;

import com.webgame.webgame.dto.OrderDetailsDto;
import com.webgame.webgame.dto.UserDto;
import com.webgame.webgame.dto.UserLoginDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.*;
import com.webgame.webgame.repository.OrderRepository;
import com.webgame.webgame.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/user/orders")
    public String getUserOrders(Model model) {
        // Lấy email của người dùng đã đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm user từ email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng với email: " + email);
        }

        // Lấy danh sách đơn hàng của người dùng
        List<Orders> orders = orderRepository.findByUser(user);

        // Lấy thông tin mỗi ordẻ
        List<OrderDetailsDto> orderDetailsList = new ArrayList<>();
        for (Orders order : orders) {
            for (AccountGame accountGame : order.getAccountGames()) {
                // Tạo dto để trả về thông tin game, tài khoản, mật khẩu và giá tiền
                OrderDetailsDto details = new OrderDetailsDto();
                details.setGameName(accountGame.getGame().getGameName());
                details.setUsername(accountGame.getUsername());
                details.setPassword(accountGame.getPassword());
                details.setPrice(accountGame.getGame().getPrice()); // Lấy giá từ Game
                orderDetailsList.add(details);
            }
        }

        model.addAttribute("orderDetailsList", orderDetailsList);

        return "orders";
    }


}