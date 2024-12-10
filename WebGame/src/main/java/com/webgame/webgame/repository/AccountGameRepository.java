package com.webgame.webgame.repository;

import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountGameRepository extends JpaRepository<AccountGame, Long> {

    @Query(value = """
    SELECT ag
    FROM AccountGame ag
    WHERE ag.game.gameId = :gameId
   """)
    List<AccountGame> accountGameByGameId(@Param("gameId") Long gameId);

    // Lấy tất cả tài khoản game theo trạng thái
    @Query("SELECT a FROM AccountGame a WHERE a.status = :status")
    List<AccountGame> findAccountGamesByStatus(@Param("status") boolean status);

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
