package com.webgame.webgame.controller;

import com.webgame.webgame.dto.AccountGameDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.model.User;
import com.webgame.webgame.repository.AccountGameRepository;
import com.webgame.webgame.repository.UserRepository;
import com.webgame.webgame.service.accountGame.AccountGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountGameController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountGameService accountGameService;
    @Autowired
    AccountGameRepository accountGameRepository;

    @GetMapping("/listAccountGame/{gameId}")
    public String accountGame(@PathVariable ("gameId") Long gameId, Model model) {

        model.addAttribute("listAccount", accountGameService.listAccountByGameId(gameId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "accountGame/listGameAccount";
    }

    @GetMapping("/addAccount/{gameId}")
    public String addAccount(@PathVariable("gameId") Long gameId, Model model) {
        AccountGameDto newAccount = new AccountGameDto();
        newAccount.setGameId(gameId); // Gán gameId vào DTO
        model.addAttribute("newAccount", newAccount);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "accountGame/new-account";
    }

    @PostMapping("/saveAccount/{gameId}")
    public String saveAccount(@PathVariable("gameId") Long gameId,
                              @ModelAttribute("newAccount") AccountGameDto accountGameDto,
                              RedirectAttributes redirectAttributes) {
        try {
            if (gameId == null || accountGameDto.getUsername() == null || accountGameDto.getUsername().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Game ID and Username are required!");
                return "redirect:/addAccount/" + gameId;
            }

            // Gọi repository để kiểm tra tài khoản dựa trên username
            AccountGame existingAccount = accountGameRepository.findByUsername(accountGameDto.getUsername());

            // Nếu username chưa tồn tại, tiến hành lưu
            if (existingAccount == null) {
                AccountGame newAccount = new AccountGame();
                newAccount.setUsername(accountGameDto.getUsername());
                newAccount.setPassword(accountGameDto.getPassword());
                newAccount.setStatus(false); // Default status
                newAccount.setGame(new Game(gameId));
                accountGameRepository.save(newAccount);

                redirectAttributes.addFlashAttribute("success", "Account saved successfully!");
                return "redirect:/listAccountGame/" + gameId;
            }

            // Nếu username đã tồn tại, kiểm tra gameId
            if (!existingAccount.getGame().getGameId().equals(gameId)) {
                AccountGame newAccount = new AccountGame();
                newAccount.setUsername(accountGameDto.getUsername());
                newAccount.setPassword(accountGameDto.getPassword());
                newAccount.setStatus(false); // Default status
                newAccount.setGame(new Game(gameId));
                accountGameRepository.save(newAccount);

                redirectAttributes.addFlashAttribute("success", "Account saved successfully!");
                return "redirect:/listAccountGame/" + gameId;
            }
            redirectAttributes.addFlashAttribute("error", "This username already exists for the same game!");
            return "redirect:/addAccount/" + gameId;
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while saving the account. Please try again.");
            return "redirect:/addAccount/" + gameId;
        }
    }


    @GetMapping("/showFormEditAccount/{accountId}")
    public String editAccount(@PathVariable("accountId") Long accountId, Model model) {
        AccountGameDto accountDto = accountGameService.getAccountById(accountId); // Lấy thông tin account

        model.addAttribute("updateAccount", accountDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "accountGame/update-account"; // View form sửa account
    }

    @PostMapping("/editAccount/{gameId}/{accountId}")
    public String updateAccount(@PathVariable("gameId") Long gameId,
                                @PathVariable("accountId") Long accountId,
                                @ModelAttribute ("updateAccount") AccountGameDto accountGameDto,
                                RedirectAttributes redirectAttributes) {

        try {
            if (accountId == null || gameId == null || accountGameDto.getGameId() == null) {
                redirectAttributes.addFlashAttribute("error", "Account ID or Game ID is missing!");
                return "redirect:/listAccountGame/" + accountGameDto.getGameId();
            }
            accountGameDto.setGameId(gameId);

            // Gọi service để cập nhật account
            accountGameService.updateAccount(accountId, accountGameDto);

            return "redirect:/listAccountGame/" + accountGameDto.getGameId();

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating the account. Please try again.");
            return "redirect:/showFormEditAccount/" + accountId;
        }
    }

    @GetMapping("/deleteAccount/{accountGameId}")
    public String deleteAccount(@PathVariable("accountGameId") Long accountGameId, RedirectAttributes redirectAttributes) {
        try {
            accountGameService.deleteAccountGameById(accountGameId);
            redirectAttributes.addFlashAttribute("message", "Account deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account: " + e.getMessage());
        }
        return "redirect:/admin";
    }


}
