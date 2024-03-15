package com.model2.mvc.service.category.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.category.CategoryDao;
import com.model2.mvc.service.domain.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public CategoryDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addCategory(Category category) throws Exception {
		sqlSession.insert("CategoryMapper.addCategory", category);
	}

	@Override
	public Category getCategory(int categoryNo) throws Exception {
		return sqlSession.selectOne("CategoryMapper.getCategory", categoryNo);
	}

	@Override
	public List<Category> getCategoryList() throws Exception {
		return sqlSession.selectList("CategoryMapper.getCategoryList");
	}

	@Override
	public void deleteCategory(int categoryNo) throws Exception {
		sqlSession.delete("CategoryMapper.deleteCategory", categoryNo);
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		sqlSession.update("CategoryMapper.updateCategory", category);
		
	}

}
