package com.web.offood.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_debt")
public class RestaurantDebt {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Basic
    @Column(name = "amount_of_money")
    private Float amountOfMoney;
    @Basic
    @Column(name = "payable_date")
    private Timestamp payableDate;
    @Basic
    @Column(name = "start_date")
    private Timestamp startDate;
    @Basic
    @Column(name = "is_paid")
    private Boolean isPaid;
}
