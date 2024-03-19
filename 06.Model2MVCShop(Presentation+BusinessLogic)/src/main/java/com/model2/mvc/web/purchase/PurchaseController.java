package com.model2.mvc.web.purchase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
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
	
	
	@PostMapping("/addPurchase")
	public ModelAndView addPurchase(@ModelAttribute("purchase")Purchase purchase, @RequestParam("prodNo") int prodNo,
						@SessionAttribute(name ="user", required = false) User user) throws Exception {
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/purchase/purchaseView.jsp");
		mv.addObject("purchase", purchase);
		
		return mv;
	}
	
	@GetMapping("/addPurchase")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		Product product = productService.getProduct(prodNo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/purchase/addPurchaseView.jsp");
		mv.addObject("product", product);
		return mv;
	}
	
	@RequestMapping("/getPurchase")
	public ModelAndView getPurchaseAction(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/purchase/readPurchase.jsp");
		mv.addObject("purchase", purchase);
		
		return mv;
	}
	
	@RequestMapping("/listPurchase")
	public ModelAndView listPurchase(@SessionAttribute(name ="user", required = false) User user,
														@ModelAttribute("search") Search search, Model model) throws Exception {
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("listPurchase / search ==> " + search);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, user.getUserId());
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/purchase/listPurchase.jsp");
		mv.addObject("purchaseList", map.get("list"));
		mv.addObject("resultPage", resultPage);
		mv.addObject("search", search);
		
		return mv;
	}
	
	@PostMapping("/updatePurchase")
	public ModelAndView updatePurchase (@ModelAttribute("purchase")Purchase purchase) throws Exception {
			
		purchaseService.updatePurchase(purchase);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/getPurchase");
		
		return mv;
	}
	
	@GetMapping("/updatePurchase")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("forward:/purchase/updatePurchaseView.jsp");
		mv.addObject("purchase", purchase);
		
		return mv;
	}
	
	@GetMapping("/updateTranCode")
	public ModelAndView updateTranCode(@ModelAttribute("purchase")Purchase purchase) throws Exception {
		
		purchaseService.updateTranCode(purchase);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/listPurchase");
		mv.addObject("purchase", purchase);
		
		return mv;
	}
	
	@GetMapping("/updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode) throws Exception {
		Product product = new Product();
		product.setProdNo(prodNo);
		Purchase purchase = new Purchase();
		purchase.setTranCode(tranCode);
		purchase.setPurchaseProd(product);
		
		purchaseService.updateTranCodeByProd(purchase);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forward:/listProduct?menu=manage");
		
		return mv;
	}
}
