package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	 @Autowired
	    private CategoryRepository categoryRepository;

	    @Override
	    public List<Category> findAll() {
	        return categoryRepository.findAll();
	    }

	    @Override
	    public Category findById(Integer cateId) {
	        return categoryRepository.findById(cateId).orElse(null); // trả về null nếu không tồn tại
	    }

	    @Override
	    public Category save(Category category) {
	        return categoryRepository.save(category);
	    }

	    @Override
	    public void deleteById(Integer cateId) {
	        categoryRepository.deleteById(cateId);
	    }

	    @Override
	    public List<Category> searchByName(String cateName) {
	        return categoryRepository.findByCateNameContainingIgnoreCase(cateName);
	    }

	    @Override
	    public List<Category> findByUserId(Integer userId) {
	        return categoryRepository.findByUser_Id(userId);
	    }
}
