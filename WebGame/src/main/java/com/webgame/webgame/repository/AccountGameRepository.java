package com.webgame.webgame.repository;

import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

//    void deleteAccountGamesByAccountGameId(Long accountGameId);
    
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


//    lấy danh sách acccount chưa bán để showw cho khách hàng
    @Query("SELECT a FROM AccountGame a WHERE a.game.gameId = :gameId AND a.status = false ORDER BY a.accountGameId ASC")
    List<AccountGame> timListAccountchuaban(@Param("gameId") Long gameId);


//    Đếm số lượng game chưa bán của một game
    @Query("SELECT COUNT(a) FROM AccountGame a WHERE a.game.gameId = :gameId AND a.status = false")
    long countGamechuaban(@Param("gameId") Long gameId);

    //    Xóa hết game trong một giỏ hàng dựa vào userID (tại vì khi mua xong thì xóa giỏ hàng đi)
    @Modifying
    @Transactional
    @Query("DELETE FROM AccountGame ag WHERE ag.accountGameId = :accountGameId")
    void deleteAccountGamrById(@Param("accountGameId") Long accountGameId);

    AccountGame findByUsername(String username);



}
