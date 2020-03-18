package com.ss.CSV_To_DB.service;

import com.axisrooms.codex.model.staticlookup.Geo;
import com.ss.CSV_To_DB.model.*;
import com.ss.CSV_To_DB.repository.GeoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeoService{
    Logger logger= LoggerFactory.getLogger(GeoService.class);
    public List<String> response=new ArrayList<>();

    @Autowired
    GeoRepository goeRepository;
    public List<Geo> findAll() {
        return  goeRepository.findAllByCountry("Mexico");
    }
    public List<String> saveRecord(List<CSVData> csvDataList) {
        List<Geo> geoList=findAll();
        if(csvDataList!=null){
            csvDataList.forEach(csvData -> {
                try {
                    if ((csvData.getCountry_name() != null && !csvData.getCountry_name().isEmpty()) &&
                            (csvData.getState_Name()!=null && !csvData.getState_Name().isEmpty()) &&
                            (csvData.getCity_Name() != null)&&!csvData.getCity_Name().isEmpty()) {

                        Geo geo=getGeo(csvData,geoList);
                        if (geo == null)
                        {
                            geo = saveGeo(csvData.getCountry_name().trim(),csvData.getState_Name().trim(),csvData.getCity_Name().trim());
                            geoList.add(geo);
                            logger.info("City name ->"+geo.getCity());
                        }
                    }else response.add(csvData.getCountry_name()+"-> Not Present");
                }catch (Exception ex){
                    response.add("Error ->"+ex.getMessage());
                    logger.debug(ex.getMessage()+csvData.getCountry_name());
                }
            });
        }

        return response;
    }
    private Geo saveGeo(String countryName,String stateName,String cityName) {
        Geo geo=new Geo();
        geo.setCountry(countryName);
        geo.setState(stateName);
        geo.setCity(cityName);
        goeRepository.save(geo);
        return geo;
    }
    private Geo getGeo(CSVData csvData,List<Geo> geoList) {
        Optional<Geo> optionalCountry= Optional.ofNullable(geoList.stream().filter(geo -> geo.getCity()
                .toLowerCase().equals(csvData.getCity_Name().toLowerCase())).findAny().orElse(null));
        if(optionalCountry.isPresent()) return optionalCountry.get();
        else return null;
    }
}
