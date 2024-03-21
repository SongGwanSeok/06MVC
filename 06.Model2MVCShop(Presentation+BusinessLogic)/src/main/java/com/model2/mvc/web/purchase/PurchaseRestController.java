package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@RestController
@RequestMapping("/purchase/json/*")
public class PurchaseRestController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}") private int pageUnit;
	@Value("#{commonProperties['pageSize']}") private int pageSize;
	
	@PostMapping("/addPurchase/{prodNo}")
	public ResponseEntity<Map<String, Object>> addPurchase(@RequestBody Purchase purchase, @PathVariable int prodNo,
						@SessionAttribute(name ="user", required = false) User user) throws Exception {
		
		System.out.println("/purchase/json/addPurchase ==> start");
		Map<String, Object> map = new HashMap<String, Object>();
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		try{
			purchaseService.addPurchase(purchase);
		}catch(Exception e) {
			map.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		
		map.put("purchase", purchase);
		map.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@GetMapping("/getPurchase")
	public ResponseEntity<Map<String, Object>> getPurchaseAction(@RequestParam("tranNo") int tranNo) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Purchase purchase = purchaseService.getPurchase(tranNo);
		if(purchase == null) {
			map.put("message", "not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
		
		map.put("message", "success");
		map.put("purchase", purchase);
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@RequestMapping("/listPurchase")
	public ResponseEntity<Map<String, Object>> listPurchase(@SessionAttribute(name ="user", required = false) User user,
														@RequestBody Search search) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println("listPurchase / search ==> " + search);
		
		
		Map<String, Object> purchaseMap = null;
		try {
			purchaseMap = purchaseService.getPurchaseList(search, user.getUserId());
		}catch(Exception e) {
			map.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)purchaseMap.get("totalCount")).intValue(), pageUnit, pageSize);
		
		
		map.put("resultPage", resultPage);
		map.put("search", search);
		map.put("purchase", purchaseMap.get("list"));
		map.put("message", "success");
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@PostMapping("/updatePurchase")
	public ResponseEntity<Map<String, Object>> updatePurchase (@RequestBody Purchase purchase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {	
			purchaseService.updatePurchase(purchase);
		}catch(Exception e) {
			map.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		map.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@GetMapping("/updateTranCode")
	public ResponseEntity<Map<String, Object>> updateTranCode(@RequestBody Purchase purchase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {	
			purchaseService.updateTranCode(purchase);
		
		}catch(Exception e) {
			map.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		map.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@GetMapping("/updateTranCodeByProd")
	public ResponseEntity<Map<String, Object>> updateTranCodeByProd(@RequestParam("prodNo") int prodNo, 
																								@RequestParam("tranCode") String tranCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {	
		
			Product product = new Product();
			product.setProdNo(prodNo);
			Purchase purchase = new Purchase();
			purchase.setTranCode(tranCode);
			purchase.setPurchaseProd(product);
			
			purchaseService.updateTranCodeByProd(purchase);
		
		}catch(Exception e) {
			map.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		map.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
}
