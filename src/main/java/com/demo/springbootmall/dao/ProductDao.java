package com.demo.springbootmall.dao;

import com.demo.springbootmall.constant.ProductCategory;
import com.demo.springbootmall.dto.ProductRequest;
import com.demo.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory productCategory, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
