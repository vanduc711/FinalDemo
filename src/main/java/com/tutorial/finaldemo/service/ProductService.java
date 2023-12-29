package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProductById(int id);

    void saveProduct(Product product);

    void deleteProduct(int id);

}
