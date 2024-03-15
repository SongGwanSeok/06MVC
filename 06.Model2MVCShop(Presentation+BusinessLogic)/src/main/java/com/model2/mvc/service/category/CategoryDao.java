package com.model2.mvc.service.category;

import java.util.List;

import com.model2.mvc.service.domain.Category;

public interface CategoryDao {

	public void addCategory(Category category) throws Exception;
	
	public Category getCategory(int categoryNo) throws Exception;
	
	public List<Category> getCategoryList() throws Exception;
	
	public void deleteCategory(int categoryNo) throws Exception;
	
	public void updateCategory(Category category) throws Exception;
}
