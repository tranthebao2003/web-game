package com.webgame.webgame.service.user;


import com.webgame.webgame.model.Cart;
import com.webgame.webgame.model.Game;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CartService {
    List<Game> getAllGames();
}
