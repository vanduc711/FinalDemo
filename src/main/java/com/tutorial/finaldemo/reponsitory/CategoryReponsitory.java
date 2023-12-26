package com.tutorial.finaldemo.reponsitory;

import com.tutorial.finaldemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Integer> {
}
