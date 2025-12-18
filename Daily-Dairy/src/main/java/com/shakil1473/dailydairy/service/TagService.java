package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.entity.Tag;

import java.util.List;

public interface TagService {
    public List<TagResponseDto> getAllTags();
}
