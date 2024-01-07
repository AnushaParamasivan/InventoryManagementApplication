package com.example.retailInventory.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.retailInventory.entity.Store;
import com.example.retailInventory.exception.StoreNotFoundException;
import com.example.retailInventory.repository.StoreRepository;

@Service
public class StoreService {
	
	@Autowired
	StoreRepository storeRepo;
	

	/**
	 * To read store details from excel file and persist
	 * @param file
	 * @return
	 */
	@Transactional
	public String uploadStore( MultipartFile file) {
		List<Store> storeList = new ArrayList<>();
		
		try (InputStream inputStream = file.getInputStream()){
			try (Workbook workbook = new XSSFWorkbook(inputStream)) {
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				Store s;
				
				//iterate each row of the worksheet and create product objects and add to list for persistence
				while(rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if(row.getRowNum() == 0)
						continue;
					s = new Store();
					int i=0;
					s.setStoreId((int) row.getCell(i).getNumericCellValue());
					s.setStoreName(row.getCell(++i).getStringCellValue());
					s.setStoreLocation(row.getCell(++i).getStringCellValue());
					
					storeList.add(s);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!storeList.isEmpty()) {
			storeRepo.saveAll(storeList);
		}
		
		return "Store details file uploaded succesfully";
	}
	
	/**
	 * To find a store using its id.
	 * @param id
	 * @return
	 * @throws StoreNotFoundException
	 */
	public Store findStoreById( int id)  throws StoreNotFoundException{
		Store s1 = storeRepo.findById(id).orElseThrow(() ->new StoreNotFoundException("No store found for the requested ID."));
		return s1;
	}
	

	/**
	 * To update store details
	 * @param store
	 * @return
	 * @throws StoreNotFoundException
	 */
	@Transactional
	public Store updateStoreDetail( Store store) throws StoreNotFoundException{
		
		Store s1 = storeRepo.findById(store.getStoreId()).orElseThrow(() ->new StoreNotFoundException("No store found for the requested ID for update."));
		if(store.getStoreName() != null) {
			s1.setStoreName(store.getStoreName());
		}
		if(store.getStoreLocation() != null) {
			s1.setStoreLocation(store.getStoreLocation());
		}
		Store updatedStore = storeRepo.save(s1);
		return updatedStore;
	}
}
