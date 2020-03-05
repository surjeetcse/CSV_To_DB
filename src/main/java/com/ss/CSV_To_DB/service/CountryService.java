package com.ss.CSV_To_DB.service;

import com.ss.CSV_To_DB.model.CSVData;
import com.ss.CSV_To_DB.model.City;
import com.ss.CSV_To_DB.model.Country;
import com.ss.CSV_To_DB.model.State;
import com.ss.CSV_To_DB.repository.CityRepository;
import com.ss.CSV_To_DB.repository.CountryRepository;
import com.ss.CSV_To_DB.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService implements ICountryService {
    Logger logger= LoggerFactory.getLogger(CountryService.class);
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;

    public List<String> response=new ArrayList<>();

    @Override
    public List<Country> findAll() {
        return  (List<Country>) countryRepository.findAll();
    }
    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElse(new Country());
    }

    @Override
    public String saveRecord(List<CSVData> csvDataList){
        if(csvDataList!=null){
            csvDataList.forEach(csvData -> {
                try {
                    if ((csvData.getCountry_name() != null && !csvData.getCountry_name().isEmpty()) &&
                            (csvData.getState_Name()!=null && !csvData.getState_Name().isEmpty()) &&
                            (csvData.getCity_Name() != null)&&!csvData.getCity_Name().isEmpty()) {
                        Country country = countryRepository.findByCountry(csvData.getCountry_name().toLowerCase());
                        if (country == null)
                        {
                            country = saveCountry(csvData.getCountry_name().trim());
                        }
                        State state = stateRepository.findByStateAndCountryId(csvData.getState_Name().trim(), country.getId());
                        if (state == null) {
                            state = saveState(country, csvData.getState_Name().trim());
                        }
                        City city = cityRepository.findByCityAndStateId(csvData.getCity_Name(), state.getId());
                        if (city == null) {
                            city = saveCity(state, csvData);
                        }
                    }else response.add(csvData.getCountry_name()+"-> Not Present");
                }catch (Exception ex){
                    response.add(csvData.getCountry_name()+"-> Error");
                    logger.debug(ex.getMessage()+csvData.getCountry_name());
                }
            });
        }
        return response.toString();
    }

    private Country saveCountry(String countryName) {
        Country country=new Country();
        country.setCountry(countryName);
        countryRepository.save(country);
        return country;
    }
    private State saveState(Country country,String stateName) {
        State state=new State();
        state.setState(stateName);
        state.setCountryId(country.getId());
        stateRepository.save(state);
        return state;
    }
    private City saveCity(State state,CSVData cityData) {
        City city=new City();
        city.setCity(cityData.getCity_Name());
        city.setCode(cityData.getCity_Code()!=null?cityData.getCity_Code():"");
        city.setLat(cityData.getLat()!=null?cityData.getLat(): 0.00000000d);
        city.setLng(cityData.getLng()!=null?cityData.getLng(): 0.00000000d);
        city.setStateId(state.getId());
        cityRepository.save(city);
        return city;
    }

}
