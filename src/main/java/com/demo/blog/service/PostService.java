package com.demo.blog.service;

import com.demo.blog.dto.PaginationResponseDto;
import com.demo.blog.dto.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createUpdate(PostDto postDto, Long userId, Long categoryId);

    PaginationResponseDto<PostDto> getAllPost(int pageNo, int pageSize, String sortBy);

    void deletePost(Long postId);

    PostDto getById(Long postId);

    List<PostDto> getByCategory(Long categoryId);

    List<PostDto> getByUserId(Long userId);

    List<PostDto> findByTitle(String keyword);
}
