package com.webgame.webgame.service.accountGame;

import org.springframework.data.domain.Page;

public interface AccountGameService {
    // ở đây mình ko cần sắp xếp giảm dần theo tổng
    // sp bán được nữa vì mình đã sắp xếp trong câu query rồi
    Page<Object[]> totalAccountGameSold(int page, int size);
}
