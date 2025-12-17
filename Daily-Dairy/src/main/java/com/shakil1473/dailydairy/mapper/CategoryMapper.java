package com.shakil1473.dailydairy.mapper;

import com.shakil1473.dailydairy.domain.PostStatus;
import com.shakil1473.dailydairy.domain.dto.CategoryDto;
import com.shakil1473.dailydairy.domain.dto.CategoryRequestDto;
import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto categoryToCategoryDto(Category category);

    Category toCategoryEntity(CategoryRequestDto categoryRequestDto);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if(posts == null || posts.isEmpty()) {
            return 0;
        }

        return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}
