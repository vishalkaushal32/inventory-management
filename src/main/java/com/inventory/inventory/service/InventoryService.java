package com.inventory.inventory.service;

    

import java.io.IOException;
import java.util.List;

import com.inventory.inventory.model.Inventory;
import com.inventory.inventory.repository.InventoryRepository;
import com.inventory.inventory.utils.CsvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class  InventoryService {
 
  @Autowired
  InventoryRepository inventoryRepository;
  
  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  public List<Inventory> getAllBySupplierId(String word, boolean includeExpired, Pageable page) {

      if (includeExpired) {
          return inventoryRepository.findBySupplier(word, page);
      } else {
          return inventoryRepository.findBySupplierNonExpiredProducts(word, page);

      }
  }
  
  public void saveFileToDb(MultipartFile file) {
      try {
          List<Inventory> inventories = CsvUtils.parseCSV(file.getInputStream());
          inventoryRepository.saveAll(inventories);
      } catch (IOException e) {
          throw new RuntimeException("fail to store csv data: " + e.getMessage());
      }
  }
}