package com.ss.CSV_To_DB.controller;

import com.ss.CSV_To_DB.model.CSVData;
import com.ss.CSV_To_DB.model.Country;
import com.ss.CSV_To_DB.service.CountryService;
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

    @Autowired
    ICountryService iCountryService;

    @RequestMapping(method = RequestMethod.POST,value = "/save", produces = "text/csv")
    public String saveRecord(HttpServletResponse response) throws IOException {
        List<CSVData> csvDataList=ReadCsvToResponse.readFile();
        return iCountryService.saveRecord(csvDataList);
    }

    @RequestMapping(value = "/countries")
    public void findCountries(HttpServletResponse response) throws IOException {
        List<Country> countries = (List<Country>) iCountryService.findAll();
    }

    @RequestMapping(value = "/country/{countryId}")
    public void findCountry(@PathVariable Long countryId, HttpServletResponse response) throws IOException {
        Country country = iCountryService.findById(countryId);
    }
}
