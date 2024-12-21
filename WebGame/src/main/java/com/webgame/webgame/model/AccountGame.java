package com.webgame.webgame.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_game_id")
    private Long accountGameId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

//    status = 0: chua ban, 1 la da ban
    private boolean status;

    // loại bỏ hàm @ToString đối với thuộc tính này
    @ToString.Exclude
    @ManyToOne
    // liên kết đến bảng order và tham chiếu đến order_id
    @JoinColumn(name = "order_id")
    private Orders order;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

}
