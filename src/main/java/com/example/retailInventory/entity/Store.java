package com.example.retailInventory.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity class to persist Store details
 * @author PAnusha
 *
 */
@Entity
public class Store implements Serializable{

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	int storeId;
	String storeName;
	String storeLocation;
	
	@OneToMany(mappedBy = "store")
	List<Product> products;
	
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreLocation() {
		return storeLocation;
	}
	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}
	public Store(int storeId, String storeName, String storeLocation) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeLocation = storeLocation;
	}
	
	public Store() {
		
	}
}
