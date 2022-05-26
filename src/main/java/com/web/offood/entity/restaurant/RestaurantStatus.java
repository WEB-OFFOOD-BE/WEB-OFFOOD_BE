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
@Table(name = "restaurant_status")
public class RestaurantStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "status_name")
    private String statusName;
    @Basic
    @Column(name = "enabled")
    private Boolean enabled;
    @Basic
    @Column(name = "next_status_name")
    private String nextStatusName;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
}
