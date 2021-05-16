package com.liaole.mall.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // Spu
    private Spu spu;
    // Sku
    private List<Sku> skus;
}
