package com.webgame.webgame.repository;

import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
