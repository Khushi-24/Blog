package com.demo.blog.controller;


import com.demo.blog.dto.CategoryDto;
import com.demo.blog.dto.PostDto;
import com.demo.blog.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/addUpdatePost")
    public ResponseEntity<?> addUpdatePost(@Valid @RequestBody PostDto postDto, @NotNull @PathVariable Long userId, @NotNull @PathVariable Long categoryId){

        PostDto postDto1 = postService.createUpdate(postDto, userId, categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }

    @PostMapping("/post/user/{userId}")
    public ResponseEntity<?> findByUserId(@NotNull @PathVariable Long userId){

        List<PostDto> postDtoList = postService.getByUserId(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping("/post/category/{categoryId}")
    public ResponseEntity<?> findByCategoryId(@NotNull @PathVariable Long categoryId){

        List<PostDto> postDtoList = postService.getByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
}
