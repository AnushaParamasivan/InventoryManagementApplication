package com.example.retailInventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.retailInventory.entity.Product;
import com.example.retailInventory.exception.NegativeProductPriceException;
import com.example.retailInventory.exception.ProductNotFoundException;
import com.example.retailInventory.service.ProductInventoryService;

@RestController
public class ProductInventoryController {
	
	@Autowired
	ProductInventoryService inventoryService;


	/**
	 * To read inventory details from excel file and persist
	 * @param file
	 * @return
	 */
	@PostMapping("/upload-inventory-file")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> uploadInventoryFile(@RequestParam MultipartFile file) {
		String responseMessage = inventoryService.uploadInventoryFile(file);	
		ResponseEntity<String> entity = new ResponseEntity<String>(responseMessage, HttpStatus.OK);
		return entity;
	}
	
	/**
	 * Find a specific product using product id
	 * @param id
	 * @return
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/findProductById/{id}")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public Product findProductById(@PathVariable int id)  throws ProductNotFoundException{
		Product p1 = inventoryService.findProductById(id);
		return p1;
	}
	
	/**
	 * Find product across all stores using product name.
	 * @param name
	 * @return
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/findProductByName/{name}")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public List<Product> findProductByName(@PathVariable String name)  throws ProductNotFoundException{
		List<Product> productList = inventoryService.findProductByName(name);
		return productList;
	}
	
	/**
	 * Find a specific product using product name and store id.
	 * @param name
	 * @param storeid
	 * @return
	 * @throws ProductNotFoundException
	 */
	@GetMapping("/findProductByNameAndStoreId/{name}/{storeid}")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public Product findProductByNameAndStoreId(@PathVariable String name,@PathVariable int storeid)  throws ProductNotFoundException{
		Product p1 = inventoryService.findProductByNameAndStoreId(name,storeid);
		return p1;
	}
	
	/**
	 * Add new product
	 * @param product
	 * @return
	 */
	@PutMapping("/addProduct")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public Product addProduct(@RequestBody Product product) {
		Product p = inventoryService.addProduct(product);
		return p;
	}
	
	
	/**
	 * Find all products present in a store, using store id.
	 * @param page
	 * @param size
	 * @param storeId
	 * @return
	 */
	@GetMapping("/findAllByStoreId")
	@PreAuthorize("hasRole('ROLE_STAFF')")
    public List<Product> findAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "storeId") int storeId
    ) {
        List<Product> productList =  inventoryService.findAllProducts(page,size,storeId);
        return productList;
        
    }
	
	/**
	 * To update multiple fields in a product
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProduct")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProduct(@RequestBody Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryService.updateProduct(product);
		return p1;
	}
	
	/**
	 * To update product name
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProductName")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProductName(@RequestBody Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryService.updateProductName(product);
		return p1;
	}
	
	/**
	 * To update product sku
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProductSku")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProductSku(@RequestBody Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryService.updateProductSku(product);
		return p1;
	}
	
	/**
	 * To update product price
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProductPrice")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProductPrice(@RequestBody Product product) throws ProductNotFoundException,NegativeProductPriceException{
		
		Product p1 = inventoryService.updateProductPrice(product);
		return p1;
	}
	/**
	 * To update product currency
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProductCurrency")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProductCurrency(@RequestBody Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryService.updateProductCurrency(product);
		return p1;
	}
	/**
	 * To update product date
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@PatchMapping("/updateProductDate")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Product updateProductDate(@RequestBody Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryService.updateProductDate(product);
		return p1;
	}
	
	/**
	 * To delete a product
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
	@DeleteMapping("/deleteProduct/{productid}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable int productid) throws ProductNotFoundException{
		
		inventoryService.deleteProduct(productid);
		return new ResponseEntity<String>("Product with id "+productid+" has been deleted, if already present.", HttpStatus.OK);
	}
	
}
