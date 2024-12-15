package com.webgame.webgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// thay thế cho getter, setter
@Data
// thay thế cho constructor full param
@AllArgsConstructor
// thay thế cho constructor full no param
@NoArgsConstructor
public class UserLoginDto {
    private String email;
    private String password;
    private String role;
    private String username;
    private String phone;
}