package com.demo.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {

    private Long id;

    @NotEmpty(message = "title can't be null")
    private String title;

    @NotEmpty(message = "content can't be null")
    private String content;

    private String image = "Dummy";

    private UserDto user;

    private CategoryDto category;

    private String addedDate;
}
