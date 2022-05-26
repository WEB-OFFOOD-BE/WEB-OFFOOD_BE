package com.web.offood.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Basic
    @Column(name = "is_used")
    private Boolean isUsed;
    @Basic
    @Column(name = "is_active")
    private Boolean isActive;
}
