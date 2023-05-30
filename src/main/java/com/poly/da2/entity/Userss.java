package com.poly.da2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="users")
public class Userss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private String phone;

    private String photo;

    private String gmail;

    private String address;

    private Date createDate;

    private Date updateDate;

    //@JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;
//
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Authority> authorities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    List<Order> orders;
}
