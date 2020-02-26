package com.ss.CSV_To_DB.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.ss.CSV_To_DB.model.CSVData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ReadCsvToResponse {

    public static List<CSVData> readFile() {
        List<CSVData> csvDataList=new ArrayList<>();
        try
        {
            File file = new File("/home/axisrooms/Documents/Lists_of_cities_by_country.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(fis);         //creating Workbook instance that refers to .xlsx file
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();       //iterating over excel file
            while (itr.hasNext())
            {
                Row row = itr.next();
                CSVData emp=new CSVData();
                emp.getCSVData(row);
                csvDataList.add(emp);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return csvDataList;
    }
}
