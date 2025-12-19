package com.shakil1473.dailydairy.controller;


import com.shakil1473.dailydairy.domain.dto.TagResponseDto;
import com.shakil1473.dailydairy.domain.dto.TagCreateRequestDto;
import com.shakil1473.dailydairy.domain.dto.TagUpdateRequestDto;
import com.shakil1473.dailydairy.mapper.TagMapper;
import com.shakil1473.dailydairy.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTag(@RequestBody TagCreateRequestDto tagRequestDto) {
        return new ResponseEntity<>(
                tagService.createTags(tagRequestDto.getNames()),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable UUID id,
                                                    @RequestBody TagUpdateRequestDto tagUpdateRequestDto) {
        return new ResponseEntity<>(
                tagService.updateTag(id, tagUpdateRequestDto),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
