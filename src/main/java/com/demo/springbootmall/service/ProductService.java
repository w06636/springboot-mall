package com.demo.springbootmall.service;

import com.demo.springbootmall.dto.ProductRequest;
import com.demo.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
