package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
public class AccountGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountGame_id")
    private Long accountGameId;

    private String username;
    private String password;

//    status = 0: chua ban, 1 la da ban
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

}
