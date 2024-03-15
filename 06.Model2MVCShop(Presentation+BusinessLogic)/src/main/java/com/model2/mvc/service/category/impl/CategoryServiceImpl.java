package com.model2.mvc.service.category.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.category.CategoryDao;
import com.model2.mvc.service.category.CategoryService;
import com.model2.mvc.service.domain.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	@Qualifier("categoryDaoImpl")
	private CategoryDao categoryDao;
	
	@Override
	public Category getCategory(int categoryNo) throws Exception{
		return categoryDao.getCategory(categoryNo);
	}

	@Override
	public Map<String, Object> getCategoryList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Category> categoryList = categoryDao.getCategoryList();
		map.put("list", categoryList);
		return map;
	}
	
	@Override
	public void addCategory(Category category) throws Exception{
		categoryDao.addCategory(category);
	}
	
	@Override
	public void deleteCategory(int categoryNo) throws Exception{
		categoryDao.deleteCategory(categoryNo);
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		categoryDao.updateCategory(category);
		
	}

}
