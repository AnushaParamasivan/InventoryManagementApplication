package com.example.retailInventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.retailInventory.entity.Store;
import com.example.retailInventory.exception.StoreNotFoundException;
import com.example.retailInventory.service.StoreService;

@RestController
public class StoreController {
	
	@Autowired
	StoreService storeService;
	

	/**
	 * To read store details from excel file and persist
	 * @param file
	 * @return
	 */
	@PostMapping("/upload-store-details")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> uploadStore(@RequestParam MultipartFile file) {
		String responseMessage = storeService.uploadStore(file);	
		ResponseEntity<String> entity = new ResponseEntity<String>(responseMessage, HttpStatus.OK);
		return entity;
	}
	
	@GetMapping("/findStoreById/{id}")
	@PreAuthorize("hasRole('ROLE_STAFF')")
	public Store findStoreById(@PathVariable int id)  throws StoreNotFoundException{
		Store s1 = storeService.findStoreById(id);
		return s1;
	}
	

	/**
	 * To update store details
	 * @param store
	 * @return
	 * @throws StoreNotFoundException
	 */
	@PatchMapping("/updateStoreDetail")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Store updateStoreDetail(@RequestBody Store store) throws StoreNotFoundException{
		
		Store s1 = storeService.updateStoreDetail(store);
		return s1;
	}
}
