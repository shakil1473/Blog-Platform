package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.PostStatus;
import com.shakil1473.dailydairy.domain.dto.PostRequestDto;
import com.shakil1473.dailydairy.domain.dto.PostResponseDto;
import com.shakil1473.dailydairy.domain.dto.UserDto;
import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.domain.entity.Post;
import com.shakil1473.dailydairy.domain.entity.Tag;
import com.shakil1473.dailydairy.domain.entity.User;
import com.shakil1473.dailydairy.mapper.PostMapper;
import com.shakil1473.dailydairy.repository.PostRepository;
import com.shakil1473.dailydairy.service.CategoryService;
import com.shakil1473.dailydairy.service.PostService;
import com.shakil1473.dailydairy.service.TagService;
import com.shakil1473.dailydairy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PostMapper postMapper;
    private final UserService userService;

    private final Integer WORDS_PER_MINUTE = 200;

    @Override
    public List<PostResponseDto> getAllPublishedPosts(UUID categoryId, UUID tagId) {

        List<Post> posts= new ArrayList<>();
        if(categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            posts = postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    null
            );
        }
        else if(categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            posts = postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }
        else if(tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            posts = postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        else {
            posts = postRepository.findAllByStatus(PostStatus.PUBLISHED);
        }

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) {
            postResponseDtos.add(postMapper.toPostResponseDto(post));
        }

        return postResponseDtos;
    }

    @Override
    public List<PostResponseDto> getAllDraftPosts(UserDto userDto) {
        User user = new User();
        if(userDto.getId() != null && userDto.getEmail() != null) {
            user = userService.getUserByIdAndEmail(userDto.getId(), userDto.getEmail());
        }
        else if(userDto.getId() != null) {
            user = userService.getUserById(userDto.getId());
        }
        else if(userDto.getEmail() != null) {
            user = userService.getUserByEmail(userDto.getEmail());
        }
        else {
            throw new IllegalIdentifierException("At least one user id or email is required");
        }

        if(user == null) {
            throw new EntityNotFoundException("No user found given id and email");
        }

        List<Post> posts = postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for(Post post : posts) {
            postResponseDtos.add(postMapper.toPostResponseDto(post));
        }
        return postResponseDtos;
    }

    @Override
    public PostResponseDto CreatePost(UUID userId, PostRequestDto postRequestDto) {
        Post post = postMapper.toPost(postRequestDto);
        post.setReadingTime(calculateReadingTime(post.getContent()));

        User user = userService.getUserById(userId);
        post.setAuthor(user);

        post.setCategory(categoryService.getCategoryById(postRequestDto.getCategoryId()));

        for(UUID tagId: postRequestDto.getTagIds()) {
            Tag tag = tagService.getTagById(tagId);
            post.getTags().add(tagService.getTagById(tagId));
        }

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        return postMapper.toPostResponseDto(postRepository.save(post));
    }

    @Override
    public PostResponseDto UpdatePost(UUID userId, PostRequestDto postRequestDto) {
        Post post = postRepository.findPostById(postRequestDto.getId());
        if(post == null) {
            throw new EntityNotFoundException("Post with id " + postRequestDto.getId() + " not found");
        }

        Post updatedPost = postMapper.toPost(postRequestDto,  post);

        updatedPost.setReadingTime(calculateReadingTime(post.getContent()));
        updatedPost.setCategory(categoryService.getCategoryById(postRequestDto.getCategoryId()));
        post.getTags().removeAll(post.getTags());

        for(UUID tagId: postRequestDto.getTagIds()) {
            post.getTags().add(tagService.getTagById(tagId));
        }

        post.setUpdatedAt(LocalDateTime.now());
        return postMapper.toPostResponseDto(postRepository.save(post));
    }

    @Transactional
    @Override
    public void deletePost(UUID userId, UUID postId) {
        Post post = postRepository.findPostById(postId);
        if(post == null) {
            throw new EntityNotFoundException("Post with id " + postId + " not found");
        }

        userService.removeUserPosts(userId, post);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if(content == null || content.isEmpty()) {
            return 0;
        }
        int totalWords = content.trim().split(" ").length;
        return (int) Math.ceil((double) totalWords / WORDS_PER_MINUTE);
    }
}
