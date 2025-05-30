package com.demo.springbootmall.service.impl;

import com.demo.springbootmall.dao.ProductDao;
import com.demo.springbootmall.dao.ProductQueryParams;
import com.demo.springbootmall.dto.ProductRequest;
import com.demo.springbootmall.model.Product;
import com.demo.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {

        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {

        productDao.deleteProductById(productId);
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        return productDao.countProduct(productQueryParams);
    }
}
