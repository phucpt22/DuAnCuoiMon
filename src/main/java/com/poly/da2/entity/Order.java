package com.poly.da2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date createDate = new Date();

    @Temporal(TemporalType.DATE)
    private Date updateDate = new Date();

    private Double totalPrice;

    private Double vat;
    private String status_order;
    private String payments;

    private Double origninal_price;
    private String  address;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Userss user;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;
}
