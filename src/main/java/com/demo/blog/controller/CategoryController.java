package com.demo.blog.controller;

import com.demo.blog.dto.CategoryDto;
import com.demo.blog.service.CategoryService;
import com.demo.blog.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category/")
    public ResponseEntity<?> addUpdateCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto categoryDto1 = categoryService.addUpdate(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> categoryDtoList = categoryService.getAll();
        if(categoryDtoList.isEmpty()){
            return new ResponseEntity<>(new ApiResponse("No record found"),HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable long categoryId){
        CategoryDto categoryDto = categoryService.getById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteById(@PathVariable long categoryId){
        categoryService.delete(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
