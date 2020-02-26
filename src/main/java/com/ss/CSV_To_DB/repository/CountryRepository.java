package com.ss.CSV_To_DB.repository;

import com.ss.CSV_To_DB.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country,Long> {

    Country findByCountry(String name);
}
