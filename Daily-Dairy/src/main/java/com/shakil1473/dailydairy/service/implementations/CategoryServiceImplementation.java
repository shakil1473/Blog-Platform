package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CreateCategoryRequestDto;
import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.mapper.CategoryMapper;
import com.shakil1473.dailydairy.repository.CategoryRepository;
import com.shakil1473.dailydairy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithPostCount();
        return categories.stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toCategoryEntity(categoryRequestDto);

        if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }

        Category savedCategory = categoryRepository.save(categoryMapper.toCategoryEntity(categoryRequestDto));
        return categoryMapper.categoryToCategoryDto(savedCategory);
    }
}
