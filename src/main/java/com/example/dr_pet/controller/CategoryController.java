package com.example.dr_pet.controller;


import com.example.dr_pet.DTO.Response.CategoryResponse;
import com.example.dr_pet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> categories = new ArrayList<>();
        categories = categoryService.getAllCategory();
        if (categories.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Not found any categories");
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


}
