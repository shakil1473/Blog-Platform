package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CategoryRequestDto;


import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto);
    void deleteCategory(UUID categoryId);
}
