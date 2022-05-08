package com.inventory.inventory.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    String code;

    @Column
    String name;

    @Column
    String batch;

    @Column
    long stock;

    @Column
    long deal;

    @Column
    long free;

    @Column
    Double mrp;

    @Column
    Double rate;

    @Column
    LocalDate exp;

    @Column
    String company;
    
    @Column
    String supplier;

}
