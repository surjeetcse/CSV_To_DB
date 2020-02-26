package com.ss.CSV_To_DB.repository;

import com.ss.CSV_To_DB.model.Country;
import com.ss.CSV_To_DB.model.State;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State,Long> {
    State findByStateAndCountryId(String name,Long id);
}
