package com.ecom.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.ecom.model.Category;

public interface CategoryService {
	
	public Category saveCategory(Category category);
	
	public Boolean existCategory(String name);
	
	public List<Category> getAllCategory();
	
	//to delete item in a category
	public Boolean deleteCategory(int id);
	
	//to edit a category
	public Category getCategoryById(int id);
	
	//this is used to check which activity is active that is only shown to users. 
	public List<Category> getAllActiveCategory();
	
	public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize);
}
