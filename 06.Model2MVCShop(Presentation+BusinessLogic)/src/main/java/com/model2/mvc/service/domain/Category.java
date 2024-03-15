package com.model2.mvc.service.domain;

public class Category {

	private int categoryNo;
	private String categoryName;
	private int productCnt;
	
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getProductCnt() {
		return productCnt;
	}
	public void setProductCnt(int productCnt) {
		this.productCnt = productCnt;
	}
	
	@Override
	public String toString() {
		return "category : [ categoryNo = " + categoryNo + ", categoryName = " + categoryName + "]";
	}
}
