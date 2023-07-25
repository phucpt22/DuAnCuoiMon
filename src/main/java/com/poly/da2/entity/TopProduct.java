package com.poly.da2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TopProduct {
    @Id
    int id;
    String name;
    String url;
    long count;
    double totalMoney;

}
