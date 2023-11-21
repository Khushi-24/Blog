package com.demo.blog.service.impl;

import com.demo.blog.dto.CommentDto;
import com.demo.blog.entity.Comment;
import com.demo.blog.entity.Post;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto addComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentRepository.save(comment);
        commentDto.setId(comment.getId());
        return commentDto;
    }
}
