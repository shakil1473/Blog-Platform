package com.shakil1473.dailydairy.controller;


import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.entity.Tag;
import com.shakil1473.dailydairy.mapper.TagMapper;
import com.shakil1473.dailydairy.repository.TagRepository;
import com.shakil1473.dailydairy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        List<TagResponseDto> allTagRResponseDto = tagService.getAllTags();
        return ResponseEntity.ok(allTagRResponseDto);
    }
}
