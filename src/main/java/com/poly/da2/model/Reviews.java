package com.poly.da2.model;

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


}
