package com.poly.da2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fullName;

    private String phone;

    private String photo;

    private String gmail;

    private String address;

    private Date createDate;

    private Date updateDate;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Authority> authorities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    List<Order> orders;
}
