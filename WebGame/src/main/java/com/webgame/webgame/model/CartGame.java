package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartGame.class)
public class CartGame {
//    vi 2 thuoc tinh cartId, gameId nhan gia tri tu 2 bang khac
//    nen ko can tu dau generate nua
    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @Id
    @Column(name = "game_id")
    private Long gameId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
