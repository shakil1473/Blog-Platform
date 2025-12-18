package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.entity.Tag;
import com.shakil1473.dailydairy.mapper.TagMapper;
import com.shakil1473.dailydairy.repository.TagRepository;
import com.shakil1473.dailydairy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImplementation implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagResponseDto> getAllTags() {
        List<Tag> tags = tagRepository.findAllWithPostCount();
        return tags.stream().map(tagMapper::toTagResponseDto).toList();
    }
}
