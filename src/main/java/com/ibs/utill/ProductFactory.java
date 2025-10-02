package com.ibs.utill;

import com.ibs.pojo.ProductPojo;
import com.ibs.utill.enums.ProductType;

import java.util.UUID;

public class ProductFactory {

    public static ProductPojo randomFruit() {
        return ProductPojo.builder()
                .name("Fruit-" + UUID.randomUUID())
                .type(ProductType.FRUIT)
                .exotic(true)
                .build();
    }

    public static ProductPojo randomVegetable() {
        return ProductPojo.builder()
                .name("Vegetable-" + UUID.randomUUID())
                .type(ProductType.VEGETABLE)
                .exotic(false)
                .build();
    }
}
