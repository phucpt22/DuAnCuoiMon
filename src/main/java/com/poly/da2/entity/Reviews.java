package com.poly.da2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String contents;

    private Integer quanityStar;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;

}
