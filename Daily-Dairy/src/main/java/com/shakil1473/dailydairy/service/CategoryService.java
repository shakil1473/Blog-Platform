package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CreateCategoryRequestDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CreateCategoryRequestDto categoryRequestDto);
}
