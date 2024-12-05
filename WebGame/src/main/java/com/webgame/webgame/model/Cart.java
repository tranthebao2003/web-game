package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(name = "cart_id")
    private Long cartId;
    private int sumPrice;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cart", fetch = FetchType.EAGER)
    private Collection<CartGame> cartGames;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
