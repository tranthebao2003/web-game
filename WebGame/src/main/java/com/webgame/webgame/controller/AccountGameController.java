package com.webgame.webgame.controller;

import com.webgame.webgame.dto.AccountGameDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.UserRepository;
import com.webgame.webgame.service.accountGame.AccountGameService;
import com.webgame.webgame.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class AccountGameController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountGameService accountGameService;

    @GetMapping("/listAccountGame/{gameId}")
    public String accountGame(@PathVariable ("gameId") Long gameId, Model model) {

        model.addAttribute("listAccount", accountGameService.listAccountByGameId(gameId));

        model.addAttribute("admin", userRepository.findByRole("admin"));
        return "accountGame/listGameAccount";
    }

//    @GetMapping("/addAccount/{gameId}")
//    public String addAccount(@PathVariable ("gameId") Long gameId, Model model) {
//
//        AccountGameDto newAccount = new AccountGameDto();
//        // Gán gameId vào DTO
//        newAccount.setGameId(gameId);
//        model.addAttribute("newAccount", newAccount);
//
//        model.addAttribute("admin", userRepository.findByRole("admin"));
//        return "accountGame/new-account";
//    }
//
//    @PostMapping("/saveAccount")
//    public String saveAccount(@ModelAttribute("newAccount") AccountGameDto accountGameDto,
//                               RedirectAttributes redirectAttributes) {
//        try {
//            accountGameService.saveAccount(accountGameDto); // Lưu game vào database
//            redirectAttributes.addFlashAttribute("success", "Game đã được thêm thành công!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Thêm game thất bại!");
//        }
//        return "redirect:/addAccount";
//    }

    @GetMapping("/addAccount/{gameId}")
    public String addAccount(@PathVariable("gameId") Long gameId, Model model) {
        AccountGameDto newAccount = new AccountGameDto();
        newAccount.setGameId(gameId); // Gán gameId vào DTO
        model.addAttribute("newAccount", newAccount);
        model.addAttribute("admin", userRepository.findByRole("admin"));
        return "accountGame/new-account"; // View form thêm account
    }

//    @PostMapping("/saveAccount")
//    public String saveAccount(@ModelAttribute ("newAccount") AccountGameDto accountGameDto) throws IOException {
//        accountGameService.saveAccount(accountGameDto); // Gọi service để lưu account
//        return "redirect:/listAccountGame/" + accountGameDto.getGameId();
//    }

    @PostMapping("/saveAccount/{gameId}")
    public String saveAccount(@PathVariable("gameId") Long gameId, @ModelAttribute("newAccount") AccountGameDto accountGameDto,
                              RedirectAttributes redirectAttributes) throws IOException {
        try {
            // Kiểm tra nếu `gameId` bị null
            if (accountGameDto.getGameId() == null) {
                redirectAttributes.addFlashAttribute("error", "Game ID is required!");
                return "redirect:/addAccount/" + accountGameDto.getGameId(); // Đường dẫn thay thế khi dữ liệu không hợp lệ
            }

            // Gán gameId từ URL vào DTO
            accountGameDto.setGameId(gameId);

            // Gọi service để lưu account
            accountGameService.saveAccount(accountGameDto);
            return "redirect:/listAccountGame/" + accountGameDto.getGameId();
        } catch (Exception ex) {

            // Xử lý lỗi và thông báo tới người dùng
            redirectAttributes.addFlashAttribute("error", "An error occurred while saving the account. Please try again.");
            return "redirect:/addAccount/" + accountGameDto.getGameId(); // Đường dẫn thay thế khi có lỗi
        }
    }


    @GetMapping("/showFormEditAccount/{accountId}")
    public String editAccount(@PathVariable("accountId") Long accountId, Model model) {
        AccountGameDto accountDto = accountGameService.getAccountById(accountId); // Lấy thông tin account

        model.addAttribute("updateAccount", accountDto);
        model.addAttribute("admin", userRepository.findByRole("admin"));
        return "accountGame/update-account"; // View form sửa account
    }

    @PostMapping("/editAccount/{gameId}/{accountId}")
    public String updateAccount(@PathVariable("gameId") Long gameId,
                                @PathVariable("accountId") Long accountId,
                                @ModelAttribute ("updateAccount") AccountGameDto accountGameDto,
                                RedirectAttributes redirectAttributes) {

        try {
            // Kiểm tra nếu `gameId` hoặc `accountId` bị null
            if (accountId == null || gameId == null || accountGameDto.getGameId() == null) {
                redirectAttributes.addFlashAttribute("error", "Account ID or Game ID is missing!");
                return "redirect:/listAccountGame/" + accountGameDto.getGameId();
            }

            // Gán `gameId` từ URL vào DTO (nếu cần)
            accountGameDto.setGameId(gameId);

            // Gọi service để cập nhật account
            accountGameService.updateAccount(accountId, accountGameDto);

            // Redirect về danh sách tài khoản sau khi cập nhật thành công
            return "redirect:/listAccountGame/" + accountGameDto.getGameId();

        } catch (Exception ex) {

            // Thông báo lỗi tới người dùng
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating the account. Please try again.");
            return "redirect:/showFormEditAccount/" + accountId; // Đường dẫn khi có lỗi
        }
    }

//    @GetMapping("/deleteAccount/{accountId}")
//    public String deleteAccount(@PathVariable("accountId") Long accountId) {
//        Long gameId = accountGameService.deleteAccount(accountId); // Gọi service để xóa và trả về gameId
//        return "redirect:/listAccountGame/" + gameId;
//    }


    @GetMapping("/deleteAccount/{gameId}/{accountId}")
    public String deleteAccount(@PathVariable("gameId") Long gameId,
            @PathVariable("accountId") Long accountId, RedirectAttributes redirectAttributes) {
//        Long gameId = accountGameService.deleteAccount(accountId); // Lấy gameId sau khi xóa
        accountGameService.deleteAccount(accountId);
        System.out.println("Xóa tài khoản với ID: " + accountId);

        return "redirect:/listAccountGame/" + gameId;
    }
}
