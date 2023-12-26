package com.tutorial.finaldemo.service;

import com.tutorial.finaldemo.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    Category getCategoryById(int id);

    Category saveCategory(Category category);

    void deleteCategoryById(int id);
}
