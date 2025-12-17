package com.shakil1473.dailydairy.repository;

import com.shakil1473.dailydairy.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

}
