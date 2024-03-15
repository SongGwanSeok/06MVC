package com.model2.mvc.service.category;

import java.util.Map;

import com.model2.mvc.service.domain.Category;

public interface CategoryService {
	
	public Category getCategory(int categoryNo) throws Exception; 

	public Map<String, Object> getCategoryList() throws Exception;
	
	public void addCategory(Category category) throws Exception;
	
	public void deleteCategory(int categoryNo) throws Exception;
	
	public void updateCategory(Category category) throws Exception;
	
}

