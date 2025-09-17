package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Category;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Integer id);

    Category save(Category category);

    void deleteById(Integer id);

    List<Category> searchByName(String keyword);

    List<Category> findByUserId(Integer userId);
}
