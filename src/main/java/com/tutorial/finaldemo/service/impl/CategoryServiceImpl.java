package com.tutorial.finaldemo.service.impl;

import com.tutorial.finaldemo.entity.Category;
import com.tutorial.finaldemo.reponsitory.CategoryReponsitory;
import com.tutorial.finaldemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryReponsitory categoryReponsitory;


    public List<Category> getAllCategory() {
        return categoryReponsitory.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryReponsitory.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) { categoryReponsitory.save(category);
        return category;
    }

    public void deleteCategoryById(int id){
        categoryReponsitory.deleteById(id);
    }
}
