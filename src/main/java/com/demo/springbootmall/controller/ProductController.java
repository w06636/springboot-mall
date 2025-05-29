package com.demo.springbootmall.controller;

import com.demo.springbootmall.constant.ProductCategory;
import com.demo.springbootmall.dao.ProductQueryParams;
import com.demo.springbootmall.dto.ProductRequest;
import com.demo.springbootmall.model.Product;
import com.demo.springbootmall.service.ProductService;
import com.demo.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated // 有用到@Max跟@Min時須加上此annotation
@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category, // 參數可選填
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "create_date") String orderBy, // 商品排序，預設依create_date排序
            @RequestParam(defaultValue = "desc") String sort, // 升序or降序，預設desc
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 分頁，取得幾筆(最大1000最小0)
            @RequestParam(defaultValue = "0") @Min(0) Integer offset // 分頁，跳過幾筆(最小0)
    ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);
        Integer total = productService.countProduct(productQueryParams);

        // 設定分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        log.info("查無商品");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        Product p = productService.getProductById(productId);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId, productRequest);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
