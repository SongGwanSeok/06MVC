package com.model2.mvc.web.category;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.service.category.CategoryService;
import com.model2.mvc.service.domain.Category;

@Controller
public class CategoryController {
	
	@Autowired
	@Qualifier("categoryServiceImpl")
	private CategoryService categoryService;
	
	public CategoryController() {
		System.out.println(this.getClass());
	}
	
	@PostMapping("/addCategory.do")
	public String addCategory(@ModelAttribute("category") Category category) throws Exception {
		categoryService.addCategory(category);
		
		return "forward:/listCategory.do";
	}

	@GetMapping("/deleteCategory.do")
	public String deleteCategory(@RequestParam int categoryNo) throws Exception {
		categoryService.deleteCategory(categoryNo);
		
		return "forward:/listCategory.do";
	}
	
	@RequestMapping("/listCategory.do")
	public String listCategory(Model model) throws Exception{
		Map<String, Object> map = categoryService.getCategoryList();
		model.addAttribute("list", map.get("list"));
		
		return "forward:/category/viewCategory.jsp";
	}
	
	@PostMapping("/updateCategory.do")
	public String updateCategory(@RequestParam("fcategoryNo")int categoryNo, 
															@RequestParam("newCategoryName") String newCategoryName) throws Exception{
		Category category = new Category();
		category.setCategoryNo(categoryNo);
		category.setCategoryName(newCategoryName);
		categoryService.updateCategory(category);
		
		return "forward:/listCategory.do";
	}
}
