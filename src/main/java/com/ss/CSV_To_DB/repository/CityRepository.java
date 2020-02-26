package com.ss.CSV_To_DB.repository;

import com.ss.CSV_To_DB.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CityRepository extends CrudRepository<City,Long> {
    City findByCityAndStateId(String name,Long id);
}
