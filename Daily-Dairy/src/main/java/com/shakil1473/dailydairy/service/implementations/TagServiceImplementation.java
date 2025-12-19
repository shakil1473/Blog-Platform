package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.dto.TagUpdateRequestDto;
import com.shakil1473.dailydairy.domain.entity.Tag;
import com.shakil1473.dailydairy.mapper.TagMapper;
import com.shakil1473.dailydairy.repository.TagRepository;
import com.shakil1473.dailydairy.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Transactional
    @Override
    public List<TagResponseDto> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build()
                )
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        List<TagResponseDto> tagResponseDtos = savedTags.stream().map(tagMapper::toTagResponseDto).toList();
        return tagResponseDtos;
    }

    @Override
    public TagResponseDto updateTag(UUID id, TagUpdateRequestDto tagUpdateRequestDto) {
        Tag existingTag = tagRepository.findById(id).orElse(null);
        if(existingTag == null) {
            throw new IllegalArgumentException("Tag with id " + id + " does not exist");
        }
        else {
            existingTag.setName(tagUpdateRequestDto.getUpdateName());
            existingTag = tagRepository.save(existingTag);
        }

        return tagMapper.toTagResponseDto(existingTag);
    }

    @Override
    public void deleteTag(UUID id) {
        Tag existingTag = tagRepository.findById(id).orElse(null);
        if(existingTag == null) {
            throw new IllegalArgumentException("Tag with id " + id + " does not exist");
        }

        tagRepository.deleteById(id);
    }
}
