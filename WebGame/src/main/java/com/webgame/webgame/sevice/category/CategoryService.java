package com.webgame.webgame.sevice.category;

import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategoryList();
    Category getCategoryById(Long id);
}
