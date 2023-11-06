package com.demo.blog.config;

import com.demo.blog.dto.UserDto;
import com.demo.blog.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        TypeMap<User, UserDto> propertyMapper = modelMapper.createTypeMap(User.class, UserDto.class);
//        propertyMapper.addMappings(mapper -> mapper.skip(UserDto :: setUserPassword));
////        modelMapper.typeMap(User.class, UserDto.class)
////                .addMappings(mapping -> {
////                    mapping.map(User::getId, UserDto ::setUserId);
////                    mapping.map(User::getAbout, UserDto ::setUserAbout);
////                    mapping.map(User::getEmail, UserDto ::setUserEmail);
////                    mapping.map(User::getName, UserDto ::setUserName);
////                });
//
//        modelMapper.typeMap(UserDto.class, User.class)
//                .addMappings(mapping -> {
//                    mapping.map(UserDto ::getUserId, User::setId);
//                    mapping.map(UserDto ::getUserAbout, User::setAbout);
//                    mapping.map(UserDto ::getUserEmail, User::setEmail);
//                    mapping.map(UserDto ::getUserPassword, User::setPassword);
//                    mapping.map(UserDto ::getUserName, User::setName);
//                });
        return modelMapper;
    }
}
