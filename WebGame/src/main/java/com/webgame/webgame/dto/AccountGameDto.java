package com.webgame.webgame.dto;

import lombok.Data;

@Data
public class AccountGameDto {
    private Long gameId;
    private String username;
    private String password;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
