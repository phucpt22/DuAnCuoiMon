package com.poly.da2.model;
import com.poly.da2.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductPageOutPut {
    List<Product> products;
    Double price;
    int totalPage;
}
