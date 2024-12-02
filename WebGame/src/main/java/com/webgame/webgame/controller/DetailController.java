package com.webgame.webgame.controller;

import com.webgame.webgame.model.Game;
import com.webgame.webgame.model.Review;
import com.webgame.webgame.repository.GameRepository;
import com.webgame.webgame.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DetailController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/game/{id}")
    public String getGameDetails(@PathVariable Long id, Model model) {
        // Lấy thông tin chi tiết của game
        Game game = gameRepository.findById(id).orElse(null);
        model.addAttribute("game", game);

        // Lấy danh sách đánh giá liên quan đến game
        List<Review> reviews = reviewRepository.findByGameGameId(id);
        model.addAttribute("reviews", reviews);

        return "chitietsanpham/chitietsanpham"; // Tên file HTML
    }
}
