package com.shakil1473.dailydairy.controller;


import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CategoryRequestDto;
import com.shakil1473.dailydairy.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoriesDto = categoryService.getAllCategories();
        return ResponseEntity.ok(categoriesDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return new ResponseEntity<>(
                categoryService.createCategory(categoryRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID categoryId,
                                                      @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        return new ResponseEntity<>(
                categoryService.updateCategory(categoryId, categoryRequestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
