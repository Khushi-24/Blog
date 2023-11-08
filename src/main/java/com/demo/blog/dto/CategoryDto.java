package com.demo.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {


    private Long categoryId;

    @NotEmpty(message = "categoryName can't be empty")
    private String categoryName;

    @NotEmpty(message = "categoryAbout can't be empty")
    private String categoryAbout;
}
