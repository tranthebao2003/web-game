package com.webgame.webgame.repository;

import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountGameRepository extends JpaRepository<AccountGame, Long> {
    // vì câu query này làm việc với entity nên sẽ khác 1 chút
    // so với câu query làm vc với database
    // giải thích có ghi trong file note web game

    @Query("SELECT new com.webgame.webgame.dto.gameDto.GameSaleDto(" +
            "ag.game.gameId, ag.game.gameName, ag.game.price, ag.game.gameImg, COUNT(ag.game.gameId)) " +
            "FROM AccountGame ag " +
            "WHERE ag.status = true " +
            "GROUP BY ag.game.gameId, ag.game.gameName, ag.game.price, ag.game.gameImg " +
            "ORDER BY COUNT(ag.game.gameId) DESC")
    Page<GameSaleDto> findTopSellingGames(Pageable pageable);
}
