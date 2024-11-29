package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CategoryGame.class)
public class CategoryGame {
    //    vi 2 thuoc tinh cartId, gameId nhan gia tri tu 2 bang khac
//    nen ko can tu dau generate nua
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
