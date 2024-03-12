package com.model2.mvc.web.purchase;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


@Controller
public class PurchaseController {

	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}") private int pageUnit;
	@Value("#{commonProperties['pageSize']}") private int pageSize;
	
	
	@PostMapping("/addPurchase.do")
	public String addPurchase(@ModelAttribute("purchase")Purchase purchase, @Param("prodNo") int prodNo,
						@SessionAttribute(name ="user", required = false) User user, Model model) throws Exception {
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		purchaseService.addPurchase(purchase);
		model.addAttribute(purchase);
		
		return "forward:/purchase/purchaseView.jsp";
	}
	
	@GetMapping("/addPurchaseView.do")
	public String addPurchaseView(@Param("prodNo") int prodNo, Model model) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchaseAction() {
		return null;
	}
	
	@GetMapping("/listPurchase.do")
	public String listPurchase() {
		return null;
	}
	
	@PostMapping("/updatePurchase.do")
	public String updatePurchase () {
		
		return null;
	}
	
	@GetMapping("/updatePurchaseView.do")
	public String updatePurchaseView() {
		
		return null;
	}
	
	@PostMapping("/updateTranCode.do")
	public String updateTranCode() {
		return null;
	}
	
	@PostMapping("/updateTranCodeByProd.do")
	public String updateTranCodeByProd() {
		return null;
	}
}
