package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    ProductService productService;

}
