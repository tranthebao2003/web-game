package com.webgame.webgame.repository;



import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // phương thức này jpa nó có sẵn
    // tìm kiếm all game chứa gameName mà user truyền vào
    // ko phân biệt hoa thường
    List<Game> findGamesByGameNameContainingIgnoreCase(String searchInput);
    List<Game> findByCategoryGames_Category(Category category);
  
}
