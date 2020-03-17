package com.ss.CSV_To_DB.controller;

import com.ss.CSV_To_DB.model.CSVData;
import com.ss.CSV_To_DB.model.Country;
import com.ss.CSV_To_DB.service.GeoService;
import com.ss.CSV_To_DB.service.ICountryService;
import com.ss.CSV_To_DB.util.ReadCsvToResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MyCountryController {

    //localhost:4000/api/save

    @Autowired
    ICountryService iCountryService;
    @Autowired
    GeoService geoService;
    @RequestMapping(method = RequestMethod.POST,value = "/save", produces = "text/csv")
    public String saveRecordPostgres(HttpServletResponse response) throws IOException {
        List<CSVData> csvDataList=ReadCsvToResponse.readFile();
        return iCountryService.saveRecord(csvDataList);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/saveMongo", produces = "text/csv")
    public String saveRecordMongo(HttpServletResponse response) throws IOException {
        List<CSVData> csvDataList=ReadCsvToResponse.readFile();
        return geoService.saveRecord(csvDataList).toString();
    }
}
