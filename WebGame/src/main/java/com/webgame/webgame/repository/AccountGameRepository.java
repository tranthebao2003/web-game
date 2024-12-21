package com.webgame.webgame.repository;

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

    // mình dùng câu query native và sắp xếp theo cả 2 trường
    // đầu tiên ưu tiên sắp xếp theo total_accounts đã bán
    // sau đó nếu số lượng account đã bán bằng nhau thì sắp xếp
    // tăng dần theo theo gameId nếu ko có 1 trường sắp xếp phụ
    // thì dữ liệu trả về sẽ bị trùng lặp
    @Query(value = """
     SELECT
            game.game_id,
            game.game_name,
            game.price,
            game.game_img,
            COUNT(account_game.account_game_id) AS total_accounts
        FROM
            game
        LEFT JOIN
            account_game
        ON
            game.game_id = account_game.game_id AND account_game.status = 1
        GROUP BY
            game.game_id, game.game_name, game.price, game.game_img
        ORDER BY
            total_accounts DESC, game.game_id ASC
   """, nativeQuery = true)
    Page<Object[]> findTopSellingGames(Pageable pageable);


//    lấy danh sách acccount chưa bán để showw cho khách hàng
    @Query("SELECT a FROM AccountGame a WHERE a.game.gameId = :gameId AND a.status = false ORDER BY a.accountGameId ASC")
    List<AccountGame> timListAccountchuaban(@Param("gameId") Long gameId);


//    Đếm số lượng game chưa bán của một game
    @Query("SELECT COUNT(a) FROM AccountGame a WHERE a.game.gameId = :gameId AND a.status = false")
    long countGamechuaban(@Param("gameId") Long gameId);

    @Modifying
    @Transactional
    @Query("DELETE FROM AccountGame ag WHERE ag.accountGameId = :accountGameId")
    void deleteAccountGameById(@Param("accountGameId") Long accountGameId);

    AccountGame findByUsername(String username);

    // phục vụ cho việc xóa game, bởi vì xóa game thì phải xóa những
    // dữ liệu có tham chiếu đến game đó
    @Modifying
    @Transactional
    @Query("DELETE FROM AccountGame ag WHERE ag.game.gameId = :gameId")
    void deleteAccountGameByGameId(@Param("gameId") Long gameId);
}
