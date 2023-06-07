package com.poly.da2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table
@Entity(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String contents;

    private Integer quanityStar;

    private Date createDate = new Date();

    private String nameReviewer;

    private String emailReviewer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userss user_id;

}
