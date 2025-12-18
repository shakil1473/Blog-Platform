package com.shakil1473.dailydairy.repository;

import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
    List<Tag> findAllWithPostCount();
}
