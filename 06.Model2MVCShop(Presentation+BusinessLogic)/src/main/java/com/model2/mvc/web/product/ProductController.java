package com.model2.mvc.web.product;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.category.CategoryService;
import com.model2.mvc.service.domain.Category;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
//	private final String fileDir = "C:\\workspace\\07.Model2MVCShop(URI,pattern)\\src\\main\\webapp\\images\\uploadFiles\\"; 

	
	@Autowired
	@Qualifier("categoryServiceImpl")
	private CategoryService categoryService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}") private int pageUnit;
	@Value("#{commonProperties['pageSize']}") private int pageSize;
	
	@GetMapping("/addProduct")
	public String addproductView(Model model) throws Exception {
		Map<String, Object> map =  categoryService.getCategoryList();
		model.addAttribute("list",map.get("list"));
		System.out.println(map.get("list"));
		
		return "forward:/product/addProductView.jsp";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(HttpServletRequest request,
														@ModelAttribute("product") Product product, 
														@RequestParam MultipartFile file,
														@RequestParam("productCategory") int categoryNo, 
														Model model) throws Exception {
		
		String root = request.getServletContext().getRealPath("/images/uploadFiles")+ File.separator;
		System.out.println("/addProduct" + product);
		if(file.isEmpty()) {
			product.setFileName("default.png");
		}else {
			product.setFileName(file.getOriginalFilename());
		}
		product.setManuDate(product.getManuDate().replace("-", ""));
		product = productService.addProduct(product);
		
		if (!file.isEmpty()) {
			
	        String fullPath = root + product.getProdNo() + "_" + file.getOriginalFilename();
	        System.out.println(fullPath);
	        file.transferTo(new File(fullPath));
	    }
		
		model.addAttribute("product", product);
		
		return "forward:/product/productView.jsp";
	}
	
	@RequestMapping("/listProduct")
	public String listProduct( @ModelAttribute("search") Search search, Model model, 
			@RequestParam("menu") String menu, @RequestParam(value = "categoryNo" , required = false) Integer categoryNo) throws Exception {
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		if(search.getSearchKeyword() != null && search.getSearchKeyword().equals("1")) {
			search.setSearchKeyword('%' + search.getSearchKeyword() + '%');
		}
		if(categoryNo == null) {
			categoryNo = 0;
		}
		
		Map<String, Object> map =  productService.getProductList(search, categoryNo);

		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		if(search.getSearchKeyword() != null) {
			search.setSearchKeyword(search.getSearchKeyword().replace("%", ""));
		}
		
		Map<String, Object> categoryMap = categoryService.getCategoryList();
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("categoryList", categoryMap.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/getProduct")
	public String getProduct(@Param("prodNo") int prodNo, @Param("menu") String menu,
																	Model model, @CookieValue(value = "history", required = false) String recent
																	, HttpServletResponse response) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		if(menu.equals("manage")) {
			return "forward:/updateProductView.do";
		}else if(menu.equals("search")){
//			recent = URLDecoder.decode(recent, "euc-kr");
			if(recent == null) {
				recent = "";
			}
			System.out.println("recent : " + recent);
			
			Cookie cookie = new Cookie("history", URLEncoder.encode(recent+ "," + prodNo, "euc-kr"));
			response.addCookie(cookie);
		}
		return "forward:/product/readProduct.jsp";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception {
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?menu=ok&prodNo="+product.getProdNo();
	}
	
	@GetMapping("/updateProduct")
	public String updateProductView(@Param("prodNo") int prodNo, Model model) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
}
