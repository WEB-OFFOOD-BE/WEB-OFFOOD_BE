package com.example.websitedatmon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "food")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "food")
    private List<Order> orders;

    @OneToMany(mappedBy = "food")
    private List<Comment> comments;

    @OneToMany(mappedBy = "food")
    private List<Menu> menus;
}
