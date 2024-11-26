package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = "game_name")}
)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    private String gameImg;
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String gameType;

    private Integer quantity;

    private Date createDate;

    @OneToMany(mappedBy = "game",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<AccountGame> accountGames;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game", fetch = FetchType.EAGER)
    private Collection<Review> reviews;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game", fetch = FetchType.EAGER)
    private Collection<CartGame> cartGames;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game", fetch = FetchType.EAGER)
    private Collection<ImageGame> imageGames;
}
