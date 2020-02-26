package com.ss.CSV_To_DB.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

@Data
public class CSVData {
    String City_Name;
    String City_Code;
    String State_Name;
    String Country_name;

    public CSVData() {
    }

    public CSVData getCSVData(Row row) {
        City_Name = row.getCell(0)!=null ? row.getCell(0).toString():"";
        City_Code = row.getCell(1)!=null ? row.getCell(1).toString():"";
        State_Name = row.getCell(2)!=null ? row.getCell(2).toString():"";
        Country_name = row.getCell(3)!=null ? row.getCell(3).toString():"";
        return this;
    }
}
