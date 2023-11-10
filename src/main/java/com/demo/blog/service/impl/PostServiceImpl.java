package com.demo.blog.service.impl;

import com.demo.blog.dto.PostDto;
import com.demo.blog.entity.Category;
import com.demo.blog.entity.Post;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.repository.CategoryRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

//    @PostConstruct
    private void init(){
        /*
        //
//        TypeMap<Post, PostDto> propertyMapper = modelMapper.createTypeMap(Post.class, PostDto.class);
//        propertyMapper.addMapping(src -> src.getUser().getId(), PostDto::setUserId);
////        modelMapper.typeMap(Post.class, PostDto.class)
////                .addMapping(src -> src.getUser().getId(), PostDto::setUserId);
//
//        propertyMapper.addMapping(src -> src.getCategory().getId(), PostDto::setCategoryId);

        Converter<Post, Long> userIdConverter = context -> context.getSource().getUser().getId();
        modelMapper.createTypeMap(Post.class, PostDto.class).addMappings(mapper -> {
            mapper.using(userIdConverter).map(Post::getUser, PostDto::setUserId);
        });

// Create a custom converter for CategoryId mapping
        Converter<Post, Long> categoryIdConverter = context -> context.getSource().getCategory().getId();
        modelMapper.createTypeMap(Post.class, PostDto.class).addMappings(mapper -> {
            mapper.using(categoryIdConverter).map(Post::getCategory, PostDto::setCategoryId);
        });
         */
//
//        TypeMap<Post, PostDto> propertyMapper = modelMapper.createTypeMap(Post.class, PostDto.class);
//        propertyMapper.addMappings(mapping -> {
//            mapping.skip(PostDto::setUserId);
//            mapping.skip(PostDto::setCategoryId);
//        });

    }

    @Override
    public PostDto createUpdate(PostDto postDto, Long userId, Long categoryId) {

        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Post post;
        if(postDto.getId() != null){
           post = postRepository.findById(postDto.getId()).
                    orElseThrow(() -> new ResourceNotFoundException("Post", "id", postDto.getId()));
           post = dtoToEntity(postDto);
           post.setUser(user);
           post.setCategory(category);
           post.setAddedDate(new Date());
           postRepository.save(post);
        }else {
            post = dtoToEntity(postDto);
            post.setUser(user);
            post.setCategory(category);
            post.setAddedDate(new Date());
            postRepository.save(post);
        }
        return entityToDto(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public PostDto getById(Long postId) {
        return null;
    }

    @Override
    public List<PostDto> getByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> postList = postRepository.findByCategory(category);
        return  postList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getByUserId(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Post> postList = postRepository.findByUser(user);
        return  postList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private PostDto entityToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }

    private Post dtoToEntity(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }
}
