package com.demo.blog.service.impl;

import com.demo.blog.dto.UserDto;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.service.UserService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostConstruct
    private void configureMappings() {

        try{
            TypeMap<User, UserDto> propertyMapper = modelMapper.createTypeMap(User.class, UserDto.class);
        propertyMapper.addMappings(mapper -> mapper.skip(UserDto :: setUserPassword));

//            PropertyMap<User, UserDto> userMap = new PropertyMap<>() {
//                protected void configure() {
//                    skip().setUserPassword(null);
//                }
//            };
//            modelMapper.addMappings(userMap);
        }catch (Exception e){
            System.out.println(e.getMessage() + e);
//            log.error("Error during initialization: " + e.getMessage(), e);
        }

    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return userTodto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        user = dtoToUser(userDto);
        user.setId(userId);
        userRepository.save(user);
        userDto.setUserId(userId);
        return userDto;
    }

    @Override
    public UserDto getById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        return userTodto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> user = userRepository.findAll();
        List<UserDto> userDtoList = user.stream().map(this::userTodto).toList();
        return userDtoList;
    }

    @Override
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto updateSpecificUserField(long userId, Map<String, Object> fields) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        fields.forEach((key, value) ->
        {
            Field field = ReflectionUtils.findField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, value);
        });

        userRepository.save(user);
        return userTodto(user);
    }

    public User dtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userTodto(User user){

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

}
