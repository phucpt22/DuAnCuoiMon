package com.poly.da2.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="contents")
    private String contents;

    @Column(name="quanity_star")
    private double quanity_star;

    @Column(name = "create_date")
    private Date create_date = new Date();

    @Column(name="name_reviewer")
    private String name_reviewer;

    @Column(name="email_reviewer")
    private String email_reviewer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Userss user_id;

}
