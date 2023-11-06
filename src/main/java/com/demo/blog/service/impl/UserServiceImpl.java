package com.demo.blog.service.impl;

import com.demo.blog.dto.UserDto;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.service.UserService;
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

    private void configureMappings() {

        TypeMap<User, UserDto> propertyMapper = modelMapper.createTypeMap(User.class, UserDto.class);
        propertyMapper.addMappings(modelMapper -> modelMapper.skip(UserDto :: setUserPassword));
//        modelMapper.typeMap(User.class, UserDto.class)
//                .addMappings(mapping -> {
//                    mapping.map(User::getId, UserDto ::setUserId);
//                    mapping.map(User::getAbout, UserDto ::setUserAbout);
//                    mapping.map(User::getEmail, UserDto ::setUserEmail);
//                    mapping.map(User::getName, UserDto ::setUserName);
//                });

        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapping -> {
                    mapping.map(UserDto ::getUserId, User::setId);
                    mapping.map(UserDto ::getUserAbout, User::setAbout);
                    mapping.map(UserDto ::getUserEmail, User::setEmail);
                    mapping.map(UserDto ::getUserPassword, User::setPassword);
                    mapping.map(UserDto ::getUserName, User::setName);
                });
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

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
//        user.setEmail(userDto.getEmail());
        return user;
    }

    public UserDto userTodto(User user){

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    /*
        PropertyMap<User, UserDto> userMap = new PropertyMap<User, UserDto>() {
            protected void configure() {
                skip().setPassword(null);
            }
        };
                modelMapper.addMappings(userMap);

        TypeMap<User, UserDto> propertyMapper = modelMapper.createTypeMap(User.class, UserDto.class);
        propertyMapper.addMappings(modelMapper -> modelMapper.skip(UserDto :: setPassword));
         */
}
