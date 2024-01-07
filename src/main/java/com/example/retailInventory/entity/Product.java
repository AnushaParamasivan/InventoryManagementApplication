package com.example.retailInventory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class to store product
 * @author PAnusha
 *
 */
@Entity
public class Product implements Serializable{

	@Id
	int productid;
	String sku;
	String productName;
	double price;
	String currency;
	Date updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "storeId")
	Store store;
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}

	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date date) {
		this.updatedDate = date;
	}
	public Product(int id, String sku, String productName, double price, String currency, Date date) {
		super();
		this.productid = id;
		this.sku = sku;
		this.productName = productName;
		this.price = price;
		this.currency = currency;
		this.updatedDate = date;
	}
	
	public Product() {
		
	}
	
	
	
}
