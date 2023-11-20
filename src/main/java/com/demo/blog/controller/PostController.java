package com.demo.blog.controller;


import com.demo.blog.dto.CategoryDto;
import com.demo.blog.dto.PostDto;
import com.demo.blog.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post/user/{userId}/category/{categoryId}/addUpdatePost")
    public ResponseEntity<?> addUpdatePost(@Valid @RequestBody PostDto postDto, @NotNull @PathVariable Long userId, @NotNull @PathVariable Long categoryId){

        PostDto postDto1 = postService.createUpdate(postDto, userId, categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }

    @GetMapping("/post/getAllPost")
    public ResponseEntity<?> getAllPosts(){
        List<PostDto> postDtoList = postService.getAllPost();
        if (postDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else
            return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/getPostByPostId")
    public ResponseEntity<?> getById(@NotNull @PathVariable Long postId){
        PostDto postDto = postService.getById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/post/user/{userId}")
    public ResponseEntity<?> findByUserId(@NotNull @PathVariable Long userId){

        List<PostDto> postDtoList = postService.getByUserId(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/post/category/{categoryId}")
    public ResponseEntity<?> findByCategoryId(@NotNull @PathVariable Long categoryId){

        List<PostDto> postDtoList = postService.getByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
}
