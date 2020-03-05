package com.ss.CSV_To_DB.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String city;
    @NonNull
    private Long stateId;
    private String Code;
    private double lat;
    private double lng;

    public City() {
    }
}
