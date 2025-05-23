package com.demo.springbootmall.service.impl;

import com.demo.springbootmall.dao.ProductDao;
import com.demo.springbootmall.model.Product;
import com.demo.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {

        return productDao.getProductById(productId);
    }
}
