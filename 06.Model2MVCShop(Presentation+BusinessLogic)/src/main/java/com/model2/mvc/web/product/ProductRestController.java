package com.model2.mvc.web.product;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	private final String successMsg = "성공";
	private final String failMsg = "실패";
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@GetMapping("getProduct/{productNo}")
	public Product getProduct(@PathVariable int productNo) throws Exception {
		
		Product findProduct = productService.getProduct(productNo);
		
		return findProduct;
	}
	
	@PostMapping("addProduct")
	public String addProduct(@RequestBody Product product) throws Exception {
		try {
			productService.addProduct(product);
		}catch(Exception e) {
			return failMsg;
		}
		return successMsg;
	}
	
	@GetMapping("getProductList")
	public Object getProductList(@ModelAttribute Search search, int categoryNo) throws Exception {
		Map<String, Object> map = productService.getProductList(search, categoryNo);
		
		return map.get("list");
	}
	
	@PostMapping("updateProduct")
	public String updateProduct(@ModelAttribute Product product) throws Exception {
		try {
			productService.updateProduct(product);
		}catch(Exception e) {
			return failMsg;
		}
		return successMsg;
	}
	
	
}
