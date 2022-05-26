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
@Table(name = "disk_type")
public class DiskType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "type_name")
    private String typeName;
    @Basic
    @Column(name = "is_active")
    private Boolean isActive;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
}
