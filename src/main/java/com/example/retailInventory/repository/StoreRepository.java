package com.example.retailInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.retailInventory.entity.Store;

/**
 * Repository for Store details
 * @author PAnusha
 *
 */
public interface StoreRepository extends JpaRepository<Store, Integer>{

}
