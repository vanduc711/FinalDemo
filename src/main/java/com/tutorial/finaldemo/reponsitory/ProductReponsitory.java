package com.tutorial.finaldemo.reponsitory;

import com.tutorial.finaldemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Integer> {
}
