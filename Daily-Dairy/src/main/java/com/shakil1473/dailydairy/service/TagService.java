package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.dto.TagUpdateRequestDto;
import com.shakil1473.dailydairy.domain.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    Tag getTagById(UUID tagId);
    List<TagResponseDto> getAllTags();
    List<TagResponseDto> createTags(Set<String> tagNames);
    TagResponseDto updateTag(UUID id, TagUpdateRequestDto tagUpdateRequestDto);
    void deleteTag(UUID id);
}
