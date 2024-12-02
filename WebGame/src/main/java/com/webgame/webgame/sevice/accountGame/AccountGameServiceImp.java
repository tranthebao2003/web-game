package com.webgame.webgame.sevice.accountGame;

import com.webgame.webgame.dto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.repository.AccountGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountGameServiceImp implements AccountGameService {

    @Autowired
    private AccountGameRepository accountGameRepository;

    @Override
    public Page<GameSaleDto> totalAccountGameSold(int page, int size) {
        return accountGameRepository.findTopSellingGames(PageRequest.of(page, size));
    }
}
