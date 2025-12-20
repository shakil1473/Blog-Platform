package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CategoryRequestDto;
import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.mapper.CategoryMapper;
import com.shakil1473.dailydairy.repository.CategoryRepository;
import com.shakil1473.dailydairy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(UUID categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            return category.get();
        }

        throw new EntityNotFoundException("Category with id " + categoryId + " not found");
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithPostCount();
        return categories.stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toCategoryEntity(categoryRequestDto);

        if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }

        Category savedCategory = categoryRepository.save(categoryMapper.toCategoryEntity(categoryRequestDto));
        return categoryMapper.categoryToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()) {
            throw new IllegalArgumentException("Category with id " + categoryId + " does not exist");
        }

        Category savedCategory = category.get();
        savedCategory.setName(categoryRequestDto.getName());
        Category updatedCategory = categoryRepository.save(savedCategory);
        return categoryMapper.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            if(category.get().getPosts().isEmpty()) {
                categoryRepository.delete(category.get());
            }
            else {
                throw new IllegalArgumentException("Category with id " + categoryId + " has posts associated with it");
            }
        }
    }
}
