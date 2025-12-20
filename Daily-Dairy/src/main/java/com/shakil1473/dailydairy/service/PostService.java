package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.PostResponseDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDto> getAllPosts(UUID categoryId, UUID tagId);
}
