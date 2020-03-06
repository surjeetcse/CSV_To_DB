package com.ss.CSV_To_DB.service;

import com.ss.CSV_To_DB.model.CSVData;
import com.ss.CSV_To_DB.model.City;
import com.ss.CSV_To_DB.model.Country;
import com.ss.CSV_To_DB.model.State;
import com.ss.CSV_To_DB.repository.CityRepository;
import com.ss.CSV_To_DB.repository.CountryRepository;
import com.ss.CSV_To_DB.repository.StateRepository;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        Iterable<Country> countryList=countryRepository.findAll();
        List<Country> countries= IteratorUtils.toList(countryList.iterator());

        Iterable<State> stateList=stateRepository.findAll();
        List<State> states= IteratorUtils.toList(stateList.iterator());

        Iterable<City> cityList=cityRepository.findAll();
        List<City> cities= IteratorUtils.toList(cityList.iterator());

        if(csvDataList!=null){
            csvDataList.forEach(csvData -> {
                try {
                    if ((csvData.getCountry_name() != null && !csvData.getCountry_name().isEmpty()) &&
                            (csvData.getState_Name()!=null && !csvData.getState_Name().isEmpty()) &&
                            (csvData.getCity_Name() != null)&&!csvData.getCity_Name().isEmpty()) {

                        Country country=getCountry(csvData,countries);
                        if (country == null)
                        {
                            country = saveCountry(csvData.getCountry_name().trim());
                            countries.add(country);
                            logger.info(country.toString());
                        }
                        State state=getState(csvData,states);
                        if (state == null) {
                            state = saveState(country, csvData.getState_Name().trim());
                            states.add(state);
                            logger.info(state.toString());
                        }
                        City city=getCity(csvData,cities);
                        if (city == null) {
                            city = saveCity(state, csvData);
                            cities.add(city);
                            logger.info(city.toString());
                        }
                    }else response.add(csvData.getCountry_name()+"-> Not Present");
                }catch (Exception ex){
                    response.add("Error ->"+ex.getMessage());
                    logger.debug(ex.getMessage()+csvData.getCountry_name());
                }
            });
        }
        return response.toString();
    }

    private Country getCountry(CSVData csvData,List<Country> countries) {
        Optional<Country> optionalCountry= Optional.ofNullable(countries.stream().filter(country -> country.getCountry()
                .toLowerCase().equals(csvData.getCountry_name().toLowerCase())).findAny().orElse(null));
        if(optionalCountry.isPresent()) return optionalCountry.get();
        else return null;
    }
    private State getState(CSVData csvData,List<State> stateList) {
        Optional<State> optionalState= Optional.ofNullable(stateList.stream().filter(state -> state.getState()
                .toLowerCase().equals(csvData.getState_Name().toLowerCase())).findAny().orElse(null));
        if(optionalState.isPresent()) return optionalState.get();
        else return null;
    }
    private City getCity(CSVData csvData,List<City> cityList) {
        Optional<City> optionalCity= Optional.ofNullable(cityList.stream().filter(country -> country.getCity()
                .toLowerCase().equals(csvData.getCity_Name().toLowerCase())).findAny().orElse(null));
        if(optionalCity.isPresent()) return optionalCity.get();
        else return null;
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
