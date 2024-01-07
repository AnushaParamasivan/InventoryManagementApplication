package com.example.retailInventory.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.retailInventory.entity.Product;
import com.example.retailInventory.entity.Store;
import com.example.retailInventory.exception.NegativeProductPriceException;
import com.example.retailInventory.exception.ProductNotFoundException;
import com.example.retailInventory.exception.StoreNotFoundException;
import com.example.retailInventory.repository.ProductInventoryRepository;
import com.example.retailInventory.repository.StoreRepository;

@Service
public class ProductInventoryService {
	
	@Autowired
	ProductInventoryRepository inventoryRepo;
	
	@Autowired
	StoreRepository storeRepo;
	

	/**
	 * To read inventory details from excel file and persist
	 * @param file
	 * @return
	 */
	@Transactional
	public String uploadInventoryFile( MultipartFile file) {
		List<Product> productList = new ArrayList<>();
		
		try (InputStream inputStream = file.getInputStream()){
			try (Workbook workbook = new XSSFWorkbook(inputStream)) {
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				Product p;
				
				//iterate each row of the worksheet and create product objects and add to list for persistence
				while(rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if(row.getRowNum() == 0)
						continue;
					p = new Product();
					int i=0;
					p.setProductid((int) row.getCell(i).getNumericCellValue());
					p.setSku(row.getCell(++i).getStringCellValue());
					p.setProductName(row.getCell(++i).getStringCellValue());
					p.setPrice(row.getCell(++i).getNumericCellValue());
					p.setCurrency(row.getCell(++i).getStringCellValue());
					p.setUpdatedDate(row.getCell(++i).getDateCellValue());
					int storeId = (int) row.getCell(++i).getNumericCellValue();
					Store store = storeRepo.findById(storeId).orElseThrow(() -> new StoreNotFoundException("Provided store id "+storeId+" is not associated with any existing store. Create store and then upload inventory for the store"));
					p.setStore(store);
					productList.add(p);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!productList.isEmpty()) {
			inventoryRepo.saveAll(productList);
		}
		
		return "File uploaded succesfully";
	}
	
	/**
	 * Find a specific product using product id
	 * @param id
	 * @return
	 * @throws ProductNotFoundException
	 */
	@Cacheable(cacheNames="productCache", key="#id") 
	public Product findProductById( int id)  throws ProductNotFoundException{
		Product p1 = inventoryRepo.findById(id).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID."));
		return p1;
	}
	
	/**
	 * Find product across all stores using product name.
	 * @param name
	 * @return
	 * @throws ProductNotFoundException
	 */
	public List<Product> findProductByName( String name)  throws ProductNotFoundException{
		List<Product> productList = inventoryRepo.findByProductName(name).orElseThrow(() ->new ProductNotFoundException("No product found for the requested name."));
		return productList;
	}
	
	/**
	 * Find a specific product using product name and store id.
	 * @param name
	 * @param storeid
	 * @return
	 * @throws ProductNotFoundException
	 */
	public Product findProductByNameAndStoreId( String name, int storeid)  throws ProductNotFoundException{
		Product p1 = inventoryRepo.findByProductNameAndStoreId(name,storeid).orElseThrow(() ->new ProductNotFoundException("No product found for the requested name in the mentioned store."));
		return p1;
	}
	
	/**
	 * Add new product
	 * @param product
	 * @return
	 */
	@Transactional
	public Product addProduct( Product product) {
		Product p = inventoryRepo.save(product);
		return p;
	}
	
	
	/**
	 * Find all products present in a store, using store id.
	 * @param page
	 * @param size
	 * @param storeId
	 * @return
	 */
    public List<Product> findAllProducts(int page,int size,int storeId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> responsePage =  inventoryRepo.findAllByStoreId(storeId,pageRequest);
        return responsePage.get().toList();
        
    }
	
	/**
	 * To update multiple fields in a product
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProduct( Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getProductName() != null) {
			p1.setProductName(product.getProductName());
		}
		if(product.getSku() != null) {
			p1.setSku(product.getSku());
		}
		if(product.getPrice()!= 0) {
			p1.setPrice(product.getPrice());
		}
		if(product.getCurrency() != null) {
			p1.setCurrency(product.getCurrency());
		}
		if(product.getUpdatedDate()!= null) {
			p1.setUpdatedDate(product.getUpdatedDate());
		}
		if(product.getStore() != null && product.getStore().getStoreId() != 0) {
			int storeId = product.getStore().getStoreId();
			Store store = storeRepo.findById(storeId).orElseThrow(() -> new StoreNotFoundException("Provided store id "+storeId+" is not associated with any existing store. Create store and then update product for the store"));
			p1.setStore(store);
		}
		
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}
	
	/**
	 * To update product name
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProductName( Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getProductName() != null) {
			p1.setProductName(product.getProductName());
		}
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}
	
	/**
	 * To update product sku
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProductSku( Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getSku() != null) {
			p1.setSku(product.getSku());
		}
		p1.setUpdatedDate(new Date());
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}
	
	/**
	 * To update product price
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProductPrice( Product product) throws ProductNotFoundException,NegativeProductPriceException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getPrice()> 0) {
			p1.setPrice(product.getPrice());
		}
		else {
			throw new NegativeProductPriceException("Product price has to be a positive value greater than 0");
		}
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}
	/**
	 * To update product currency
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProductCurrency( Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getCurrency() != null) {
			p1.setCurrency(product.getCurrency());
		}
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}
	/**
	 * To update product date
	 * @param product
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#product.productid")
	public Product updateProductDate( Product product) throws ProductNotFoundException{
		
		Product p1 = inventoryRepo.findById(product.getProductid()).orElseThrow(() ->new ProductNotFoundException("No product found for the requested ID for update."));
		if(product.getUpdatedDate() != null) {
			p1.setUpdatedDate(product.getUpdatedDate());
		}
		Product updatedProduct = inventoryRepo.save(p1);
		return updatedProduct;
	}

	/**
	 * To delete product
	 * @param productid
	 * @return
	 * @throws ProductNotFoundException
	 */
    @Transactional
    @CacheEvict(cacheNames = "productCache",key = "#productid")
	public void deleteProduct(int productid) {
		inventoryRepo.deleteById(productid);
	}
}
