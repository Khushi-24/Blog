package com.demo.blog.controller;


import com.demo.blog.dto.PaginationResponseDto;
import com.demo.blog.dto.PostDto;
import com.demo.blog.service.FileService;
import com.demo.blog.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/post/user/{userId}/category/{categoryId}/addUpdatePost")
    public ResponseEntity<?> addUpdatePost(@Valid @RequestBody PostDto postDto, @NotNull @PathVariable Long userId, @NotNull @PathVariable Long categoryId){

        PostDto postDto1 = postService.createUpdate(postDto, userId, categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }

    @GetMapping("/post/getAllPost")
    public ResponseEntity<?> getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
                                         @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy){
        PaginationResponseDto<PostDto> postDtoList = postService.getAllPost(pageNo, pageSize, sortBy);
        if (postDtoList.getContent().isEmpty()){
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

    @GetMapping("/post/{keyword}/getByTitleLike")
    public ResponseEntity<?> getByTitleLike(@PathVariable String keyword){
        List<PostDto> postDtoList = postService.findByTitle(keyword);
        if (postDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else
            return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<?> uploadPostImage(@RequestParam MultipartFile image, @PathVariable long postId) throws IOException {
        PostDto postDto = postService.getById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImage(fileName);
        PostDto updatedPost = postService.createUpdate(postDto, postId, postDto.getCategory().getCategoryId());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream inputStream = fileService.getResourceImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
