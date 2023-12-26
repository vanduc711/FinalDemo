package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.entity.Product;
import com.tutorial.finaldemo.reponsitory.ProductReponsitory;
import com.tutorial.finaldemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductReponsitory productReponsitory;

    public List<Product> getAllProduct() {
        return productReponsitory.findAll();
    }

    public Product getProductById(int id) {
        return productReponsitory.findById(id).orElse(null);
    }

    public void saveProduct(Product product) {
        productReponsitory.save(product);
    }

    public void deleteProduct(int id) {
        productReponsitory.deleteById(id);
    }
}
