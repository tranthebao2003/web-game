package com.webgame.webgame.service.accountGame;

import com.webgame.webgame.dto.AccountGameDto;
import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface AccountGameService {
    // ở đây mình ko cần sắp xếp giảm dần theo tổng
    // sp bán được nữa vì mình đã sắp xếp trong câu query rồi
    Page<GameSaleDto> totalAccountGameSold(int page, int size);

    List<AccountGame> listAccountByGameId(Long gameId);

    void saveAccount(AccountGameDto accountGameDto) throws IOException;
    AccountGameDto getAccountById(Long id);
    void updateAccount(Long id, AccountGameDto accountGameDto);
    void deleteAccount(Long id);
}
