package com.webgame.webgame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private Date createAt;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String role;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Orders> orders;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Review> reviews;
}
