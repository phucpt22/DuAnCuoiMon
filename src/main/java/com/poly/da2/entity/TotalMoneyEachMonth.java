package com.poly.da2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TotalMoneyEachMonth implements Serializable {
    @Id
    Integer month;
    Double totalPrice;
}
