package com.demo.springbootmall.dao.impl;

import com.demo.springbootmall.constant.ProductCategory;
import com.demo.springbootmall.dao.ProductDao;
import com.demo.springbootmall.dao.ProductQueryParams;
import com.demo.springbootmall.dto.ProductRequest;
import com.demo.springbootmall.model.Product;
import com.demo.springbootmall.rowmapper.ProductRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql = "select product_id, product_name, category, image_url, price, stock, " +
                "description, create_date, last_modified_date from product where 1 = 1";

        Map<String, Object> map = new HashMap<>();
        ProductCategory productCategory = productQueryParams.getCategory();
        String search = productQueryParams.getSearch();

        if (productCategory != null) {
            sql += " and category = :category";
            map.put("category", productCategory.name());
        }
        if (StringUtils.isNotBlank(search)) {
            sql += " and product_name like :search";
            map.put("search", "%" + search + "%");
        }
        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer productId) {

        String sql = "select product_id, product_name, category, image_url, " +
                "price, stock, description, create_date, last_modified_date " +
                "from product where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (!productList.isEmpty()) {
            return productList.get(0);
        }
        return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "insert into product (product_name,category,image_url,price,stock," +
                "description,create_date,last_modified_date) values (:productName,:category," +
                ":imageUrl,:price,:stock,:description,:createDate,:lastModifiedDate);";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date date = new Date();
        map.put("createDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 儲存資料庫自動生成的product id
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey(), "key is null").intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "update product set product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "where product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {

        String sql = "delete from product where product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
