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
public class Category {
    @Id
    @Column(name = "category_id")
    private Long categoryId;



    @Column(nullable = false)
    private String categoryName;

    private String categoryImg;

    // đối với cột có qua hệ nhìu nhìu với cột khác thì
    // thêm dòng @ToString.Exclude bỏ ToString đi
    // tránh vòng lặp vô hạn
    // ở đây category N-N game bảng trung gian là categoryGame
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<CategoryGame> categoryGames;


}
