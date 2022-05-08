package com.inventory.inventory.controller;


import java.util.HashMap;
import java.util.List;

import com.inventory.inventory.model.Inventory;
import com.inventory.inventory.service.InventoryService;
import com.inventory.inventory.utils.CsvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  @Autowired
  InventoryService inventoryService;


  @PostMapping("/upload-csv")
  public ResponseEntity<HashMap<String,String>> uploadFile(@RequestParam("file") MultipartFile file) {
      HashMap<String, String> resp = new HashMap<>();



      if (file != null && !file.isEmpty() && CsvUtils.hasCSVFormat(file)) {
          try {
              inventoryService.saveFileToDb(file);
              resp.put("message", "Uploaded the file successfully");
              return ResponseEntity.status(HttpStatus.OK).body(resp);
          } catch (Exception e) {
              resp.put("message", "Could not upload the file");
              resp.put("detail", e.getMessage());
              return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resp);
          }
      }

      resp.put("message", "Please upload a csv file!");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
  }

  
  @GetMapping("/get-inventory")
  public Object getInventory(@RequestParam String supplierCode, 
          @RequestParam boolean includeExpired, @RequestParam int pageNumber, @RequestParam int pageSize) {

      Pageable page = PageRequest.of(pageNumber, pageSize);

      return new ResponseEntity<>(inventoryService.getAllBySupplierId(supplierCode,
              includeExpired, page), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/get-all")
  public Object getAllInventory() {

      try {
          List<Inventory> inventories = inventoryService.getAllInventory();
          if (inventories.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<>(inventories, HttpStatus.OK);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
  }


  }
