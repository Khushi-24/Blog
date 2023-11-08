package com.demo.blog.service.impl;

import com.demo.blog.dto.CategoryDto;
import com.demo.blog.entity.Category;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.repository.CategoryRepository;
import com.demo.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto addUpdate(CategoryDto categoryDto) {

        if(categoryDto.getCategoryId() != null){
            Category category = categoryRepository.findById(categoryDto.getCategoryId())
                    .orElseThrow(()-> new ResourceNotFoundException("Category","id", categoryDto.getCategoryId()));
            modelMapper.map(categoryDto, category);
            category.setId(categoryDto.getCategoryId());
            categoryRepository.save(category);
            modelMapper.map(category, categoryDto);
            return categoryDto;
        }
        else {
            Category category = dtoToEntity(categoryDto);
            categoryRepository.save(category);
            return entityToDto(category);
        }
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(this::entityToDto).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public void delete(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getById(long id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return entityToDto(category);
    }

    private CategoryDto entityToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category dtoToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }
}
