package com.webgame.webgame.sevice.accountGame;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.repository.AccountGameRepository;

import java.util.List;

public interface AccountGameService {
    List<AccountGame> getAllAccountGames();
}
