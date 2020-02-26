package com.ss.CSV_To_DB.service;

import com.ss.CSV_To_DB.model.CSVData;
import com.ss.CSV_To_DB.model.Country;

import java.util.List;

public interface ICountryService {
    List<Country> findAll();
    Country findById(Long id);
    String saveRecord(List<CSVData>csvDataList);
}
