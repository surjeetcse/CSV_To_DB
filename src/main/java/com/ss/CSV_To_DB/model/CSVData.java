package com.ss.CSV_To_DB.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

@Data
public class CSVData {
    String City_Name;
    Double lat;
    Double lng;
    String Country_name;
    String State_Name;
    String City_Code;


    public CSVData() {
    }

    public CSVData getCSVData(Row row) {
        City_Name = row.getCell(0)!=null ? row.getCell(0).toString():"";
        lat=row.getCell(1)!=null?row.getCell(1).getNumericCellValue():0.00d;
        lng=row.getCell(2)!=null?row.getCell(2).getNumericCellValue():0.00d;
        Country_name = row.getCell(3)!=null ? row.getCell(3).toString():"";
        State_Name = row.getCell(4)!=null ? row.getCell(4).toString():"";
        City_Code = row.getCell(5)!=null ? row.getCell(5).toString():"";
        return this;
    }
}
