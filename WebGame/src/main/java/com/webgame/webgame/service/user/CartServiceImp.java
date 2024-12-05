package com.webgame.webgame.service.user;


import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Game> getAllGames() {
        return cartRepository.findAll();
    }

}
