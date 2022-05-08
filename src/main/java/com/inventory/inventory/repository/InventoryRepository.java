package com.inventory.inventory.repository;

import java.util.List;

import com.inventory.inventory.model.Inventory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    List<Inventory> findBySupplier(String word, Pageable pageable);

    @Query("select a from Inventory a where a.exp >= NOW() and a.supplier = :word  ")
    List<Inventory> findBySupplierNonExpiredProducts(String word, Pageable pageable);
    
}
