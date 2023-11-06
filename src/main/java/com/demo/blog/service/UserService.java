package com.demo.blog.service;

import com.demo.blog.dto.UserDto;
import com.demo.blog.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserDto addUser(UserDto user);
    UserDto updateUser(UserDto user, long userId);
    UserDto getById(long userId);
    List<UserDto> getAllUser();
    void deleteUser(long userId);

    UserDto updateSpecificUserField(long userId, Map<String, Object> fields);
}
