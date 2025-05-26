package com.demo.springbootmall.constant;

public class Test {

    public static void main(String[] args) {

        ProductCategory productCategory = ProductCategory.FOOD;
        String s = productCategory.name();

        System.out.println(ProductCategory.valueOf("CAR"));
    }
}
