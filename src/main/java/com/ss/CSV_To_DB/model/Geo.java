package com.ss.CSV_To_DB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("geo")
public class Geo {
    private String city;
    private String state;
    private String country;
    private String countryISOCode2="ME";
    private String countryISOCode3="MEX";
    private String countryisocode2="ME";
    private String countryisocode3="MEX";
    private String phoneExt="+52";
}
