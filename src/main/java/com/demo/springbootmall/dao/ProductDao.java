package com.demo.springbootmall.dao;

import com.demo.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
