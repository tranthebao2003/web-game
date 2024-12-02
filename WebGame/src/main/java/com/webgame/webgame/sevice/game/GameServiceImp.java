package com.webgame.webgame.sevice.game;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.repository.AccountGameRepository;
import com.webgame.webgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImp implements GameService {

    @Autowired
    private GameRepository GameRepository;

    @Override
    public List<AccountGame> getAllAccountGames() {
        return accountGameRepository.findAll();
    }
}
