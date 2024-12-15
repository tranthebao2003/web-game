package com.webgame.webgame.service.accountGame;

import com.webgame.webgame.repository.AccountGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AccountGameServiceImp implements AccountGameService {

    @Autowired
    private AccountGameRepository accountGameRepository;

    @Override
    public Page<Object[]> totalAccountGameSold(int page, int size) {
        return accountGameRepository.findTopSellingGames(PageRequest.of(page, size));
    }
}
