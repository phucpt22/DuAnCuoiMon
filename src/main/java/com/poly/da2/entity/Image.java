package com.poly.da2.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String thumnail_url;
}
