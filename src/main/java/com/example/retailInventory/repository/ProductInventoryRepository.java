package com.example.retailInventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.retailInventory.entity.Product;

/**
 * Repository for products
 * @author PAnusha
 *
 */
public interface ProductInventoryRepository extends JpaRepository<Product, Integer>{

	Optional<List<Product>> findByProductName(String name);

    @Query("SELECT p FROM Product p WHERE p.productName = :name AND p.store.storeId = :storeId")
	Optional<Product> findByProductNameAndStoreId(String name, int storeId);

    @Query("SELECT p FROM Product p WHERE p.store.storeId = :storeId")
	Page<Product> findAllByStoreId(int storeId, PageRequest pageRequest);


}
