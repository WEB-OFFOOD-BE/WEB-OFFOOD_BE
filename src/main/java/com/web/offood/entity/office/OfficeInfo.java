package com.web.offood.entity.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "office_info")
public class OfficeInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Basic
    @Column(name = "tax_code")
    private String taxCode;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
    @Basic
    @Column(name = "update_date")
    private Timestamp updateDate;
    @Basic
    @Column(name = "last_signed_date")
    private Timestamp lastSignedDate;
    @Basic
    @Column(name = "status_id")
    private Integer statusId;
    @Basic
    @Column(name = "is_active")
    private Boolean isActive;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "account_id")
    private Integer accountId;
    @Basic
    @Column(name = "address")
    private String address;
}
