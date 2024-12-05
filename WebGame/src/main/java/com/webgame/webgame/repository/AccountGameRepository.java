package com.webgame.webgame.repository;

import com.webgame.webgame.model.AccountGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountGameRepository extends JpaRepository<AccountGame, Long> {

    // Lấy tất cả tài khoản game theo trạng thái
    @Query("SELECT a FROM AccountGame a WHERE a.status = :status")
    List<AccountGame> findAccountGamesByStatus(@Param("status") boolean status);
}
