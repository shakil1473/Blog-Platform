package com.shakil1473.dailydairy.controller;


import com.shakil1473.dailydairy.domain.dto.PostRequestDto;
import com.shakil1473.dailydairy.domain.dto.PostResponseDto;
import com.shakil1473.dailydairy.domain.dto.UserDto;
import com.shakil1473.dailydairy.service.PostService;
import com.shakil1473.dailydairy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId
    ) {
        return ResponseEntity.ok(postService.getAllPublishedPosts(categoryId, tagId));
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostResponseDto>> getDrafts(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                postService.getAllDraftPosts(userDto)
        );
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestAttribute UUID userId,
                                                      @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(
                postService.CreatePost(userId, postRequestDto),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> updatePost(@RequestAttribute UUID userId,
                                                      @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(
                postService.UpdatePost(userId, postRequestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId, @RequestAttribute UUID userId) {
        postService.deletePost(postId,  userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}