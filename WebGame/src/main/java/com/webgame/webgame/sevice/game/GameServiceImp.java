package com.webgame.webgame.sevice.game;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.AccountGameRepository;
import com.webgame.webgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImp implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Page<Game> getGameList(int page, int size, String sortField) {
        // object sort này sắp xếp theo param sortField, theo giảm dần
        Sort sort = Sort.by(sortField).descending();
        return gameRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public List<Game> getGameSearchInput(String searchInput) {
        return gameRepository.findGamesByGameNameContainingIgnoreCase(searchInput);
    }

    @Override
    public List<Game> getGameListCategory(Category category) {
        return gameRepository.findByCategoryGames_Category(category);
    }

}
