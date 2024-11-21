package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

// vi order la tu khoa trong mysql nen phai thay thanh orders
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @CreationTimestamp
    private Date createDate;

    @CreationTimestamp
    private Date payAt;

    private BigDecimal sumPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<AccountGame> accountGames;
}
