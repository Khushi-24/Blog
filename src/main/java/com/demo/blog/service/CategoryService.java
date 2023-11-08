package com.demo.blog.service;

import com.demo.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto addUpdate(CategoryDto categoryDto);

    public List<CategoryDto> getAll();

    public void delete(long id);

    public CategoryDto getById(long id);

}
