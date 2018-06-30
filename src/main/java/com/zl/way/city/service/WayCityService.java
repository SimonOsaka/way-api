package com.zl.way.city.service;

import com.zl.way.city.model.WayCity;

import java.util.List;

public interface WayCityService {

    List<WayCity> getAllCities(byte isUsed);
}
