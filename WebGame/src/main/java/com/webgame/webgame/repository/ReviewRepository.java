package com.webgame.webgame.repository;

import com.webgame.webgame.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGameGameId(Long gameId);
}
