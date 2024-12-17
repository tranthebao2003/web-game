package com.webgame.webgame.dto;

import lombok.Data;

// @Data chính là thay thế cho getter và setter đó
@Data
public class AccountGameDto {
    private Long gameId;
    private String username;
    private String password;
}
