package com.shakil1473.dailydairy.repository;

import com.shakil1473.dailydairy.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
