package com.tutorial.finaldemo.controller;


import com.tutorial.finaldemo.entity.Category;
import com.tutorial.finaldemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping(value = "/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok("Đã tạo thành công: " + categoryService.saveCategory(category));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        var categoryName = categoryService.getCategoryById(id);
        if (categoryName != null) {
            categoryName.setName(category.getName());

            categoryService.saveCategory(categoryName);
            return ResponseEntity.ok("Cập nhật thành công id: " + id + category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Đã xóa thành công id: " + id);
    }

}
