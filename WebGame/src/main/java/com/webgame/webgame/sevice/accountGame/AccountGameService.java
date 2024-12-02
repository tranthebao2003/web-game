package com.webgame.webgame.sevice.accountGame;

import com.webgame.webgame.dto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.repository.AccountGameRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountGameService {
    // ở đây mình ko cần sắp xếp giảm dần theo tổng
    // sp bán được nữa vì mình đã sắp xếp trong câu query rồi
    Page<GameSaleDto> totalAccountGameSold(int page, int size);
}
