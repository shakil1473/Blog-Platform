package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.PostRequestDto;
import com.shakil1473.dailydairy.domain.dto.PostResponseDto;
import com.shakil1473.dailydairy.domain.dto.UserDto;
import com.shakil1473.dailydairy.domain.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDto> getAllPublishedPosts(UUID categoryId, UUID tagId);
    List<PostResponseDto> getAllDraftPosts(UserDto userDto);
    PostResponseDto CreatePost(UUID userId, PostRequestDto postRequestDto);
    PostResponseDto UpdatePost(UUID userId, PostRequestDto postRequestDto);
    void deletePost(UUID userId, UUID postId);
}
