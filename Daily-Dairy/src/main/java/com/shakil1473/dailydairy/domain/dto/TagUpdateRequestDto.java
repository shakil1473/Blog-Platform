package com.shakil1473.dailydairy.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagUpdateRequestDto {
    @NotEmpty(message = "At least one tag name is required")
    @Size(min = 2, max = 30, message = "Tag name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Tag name can only contain letters, numbers, spaces, and hyphens")
    private String updateName;

}
