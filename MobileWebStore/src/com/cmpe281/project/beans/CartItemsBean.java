package com.cmpe281.project.beans;

public class CartItemsBean {
	int index;
	int orderId;
	int userId;
	int orderItemId;
	int productId;
	int quantity;
	float price;
	float amount;
	String brandName;
	String mobileName;
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		brandName = brandName;
	}

	public String getMobileName() {
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		mobileName = mobileName;
	}

	public String getOperatingSystem() {
		return OperatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		OperatingSystem = operatingSystem;
	}

	public void setPrice(String price) {
		Price = price;
	}

	String Price;
	String OperatingSystem;


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}
