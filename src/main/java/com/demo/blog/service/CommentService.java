package com.demo.blog.service;

import com.demo.blog.dto.CommentDto;

public interface CommentService {

    CommentDto addComment(CommentDto commentDto, long postId);
}
