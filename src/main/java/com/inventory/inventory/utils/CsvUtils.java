package com.inventory.inventory.utils;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.inventory.inventory.model.Inventory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

public class CsvUtils {

    public static String TYPE = "text/csv";
    static String[] HEADERs = { "code", "name", "batch", "stock", "deal", "free", "mrp", "rate", "exp", "company",
            "supplier"
    };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")
                || file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                || file.getContentType().equals("application/vnd.ms-excel")
                || file.getContentType().equals("text/csv")
                ) {
            return true;
        }

        return false;
    }

    public static List<Inventory> parseCSV(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Inventory> inventoryList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Inventory inventory = new Inventory();
                inventory.setCode(parseString(csvRecord.get("code")));
                inventory.setName(parseString(csvRecord.get("name")));
                inventory.setBatch(parseString(csvRecord.get("batch")));
                inventory.setStock(parseLong(csvRecord.get("stock")));
                inventory.setDeal(parseLong(csvRecord.get("deal")));
                inventory.setFree(parseLong(csvRecord.get("free")));
                inventory.setMrp(parseDouble(csvRecord.get("mrp")));
                inventory.setRate(parseDouble(csvRecord.get("rate")));
                inventory.setExp(parseDate(csvRecord.get("exp")));
                inventory.setCompany(parseString(csvRecord.get("company")));
                inventory.setSupplier(parseString(csvRecord.get("supplier")));

                inventoryList.add(inventory);
            }

            return inventoryList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    // public static ByteArrayInputStream tutorialsToCSV(List<Inventory> developerTutorialList) {
    //     final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    //     try (ByteArrayOutputStream out = new ByteArrayOutputStream();
    //             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
    //         for (Inventory developerTutorial : developerTutorialList) {
    //             List<String> data = Arrays.asList(
    //                     String.valueOf(developerTutorial.getId()),
    //                     developerTutorial.getTitle(),
    //                     developerTutorial.getDescription(),
    //                     String.valueOf(developerTutorial.isPublished()));

    //             csvPrinter.printRecord(data);
    //         }

    //         csvPrinter.flush();
    //         return new ByteArrayInputStream(out.toByteArray());
    //     } catch (IOException e) {
    //         throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    //     }
    // }

    public static String parseString(String str) {

        if(str == null || str == "")
            return "-";
        return str;
    }
    
    public static long parseLong(String str) {

        if (str == null || str == "")
            return 0;

        try{
            return Long.parseLong(str);
        }
        catch(Exception e){
            return 0;
        }
    }

    public static Double parseDouble(String str) {

        if (str == null || str == "")
            return 0.0;

        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static LocalDate parseDate(String str) {

        if (str == null || str == "")
            return null;

      try{
            DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd/MM/uuuu")
                .toFormatter(Locale.ENGLISH);

            LocalDate date = LocalDate.parse(str, dateFormatter);
        
            System.out.println(date);

        return date;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return null;
      }
    }
    
}
