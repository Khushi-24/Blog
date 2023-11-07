package com.demo.blog.controller;

import com.demo.blog.dto.UserDto;
import com.demo.blog.entity.User;
import com.demo.blog.service.UserService;
import com.demo.blog.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.addUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable long userId){

        UserDto userDto1 = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateSpecificUserField(@PathVariable long userId, @RequestBody Map<String, Object> fields){

        UserDto userDto1 = userService.updateSpecificUserField(userId, fields);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId){

        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted successfully"),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUser(){

        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable long userId){

        UserDto user = userService.getById(userId);
        return  ResponseEntity.ok(user);
    }
}
