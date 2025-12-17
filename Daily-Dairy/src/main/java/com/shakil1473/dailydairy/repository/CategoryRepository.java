package com.shakil1473.dailydairy.repository;

import com.shakil1473.dailydairy.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
