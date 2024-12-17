package com.webgame.webgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// thay thế cho getter, setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsAdmin {
    private Long orderId;
    private Long userId;
    private String fullName;
    private String gameName;
    private String username;
    private String password;
    private BigDecimal price;
}
