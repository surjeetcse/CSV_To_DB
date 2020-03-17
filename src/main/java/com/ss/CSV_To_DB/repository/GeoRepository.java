package com.ss.CSV_To_DB.repository;

import com.ss.CSV_To_DB.model.Geo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoRepository extends MongoRepository<Geo, String> {
    List<Geo> findAllByCountry(String name);
}
