package com.model2.mvc.web.product;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}") private int pageUnit;
	@Value("#{commonProperties['pageSize']}") private int pageSize;
	
	@GetMapping("/addProductView.do")
	public String addproductView() throws Exception {
		return "forward:/product/addProductView.jsp";
	}
	
	@PostMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product, Model model) throws Exception {
		
		System.out.println(product);
		product.setManuDate(product.getManuDate().replace("-", ""));
		productService.addProduct(product);
		model.addAttribute(product);
		
		return "forward:/product/productView.jsp";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search, Model model, @Param("menu") String menu) throws Exception {
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		if(search.getSearchKeyword() != null && !search.getSearchKeyword().equals("")) {
			search.setSearchKeyword('%' + search.getSearchKeyword() + '%');
		}
		
		Map<String, Object> map =  productService.getProductList(search);

		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		if(search.getSearchKeyword() != null) {
			search.setSearchKeyword(search.getSearchKeyword().replace("%", ""));
		}
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
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
	
	@PostMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product) throws Exception {
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?menu=ok&prodNo="+product.getProdNo();
	}
	
	@GetMapping("/updateProductView.do")
	public String updateProductView(@Param("prodNo") int prodNo, Model model) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
}
