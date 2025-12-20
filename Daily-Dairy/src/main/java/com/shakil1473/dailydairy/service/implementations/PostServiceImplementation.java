package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.PostStatus;
import com.shakil1473.dailydairy.domain.dto.PostResponseDto;
import com.shakil1473.dailydairy.domain.entity.Category;
import com.shakil1473.dailydairy.domain.entity.Post;
import com.shakil1473.dailydairy.domain.entity.Tag;
import com.shakil1473.dailydairy.mapper.PostMapper;
import com.shakil1473.dailydairy.repository.PostRepository;
import com.shakil1473.dailydairy.service.CategoryService;
import com.shakil1473.dailydairy.service.PostService;
import com.shakil1473.dailydairy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PostResponseDto> getAllPosts(UUID categoryId, UUID tagId) {

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
}
