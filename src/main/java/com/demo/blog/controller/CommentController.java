package com.demo.blog.controller;

import com.demo.blog.dto.CommentDto;
import com.demo.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/addComment/postId/{postId}")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto commentDto1 = commentService.addComment(commentDto, postId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
}
