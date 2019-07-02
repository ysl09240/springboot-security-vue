package com.sykean.hmhc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.City;
import com.sykean.hmhc.mapper.CityMapper;
import com.sykean.hmhc.service.CityService;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

	@Override
	public RestResponse<List<City>> filedListLevel(Integer level) {
		
		 List<City> listLevel = cityMapper.filedListLevel(level);
		
		 return RestResponse.success(listLevel);
	}

	@Override
	public RestResponse<List<City>> filedListParentId(String parentCityId) {
		
		return RestResponse.success(cityMapper.filedListParentId(parentCityId));
		
	}

	@Override
	public RestResponse<String> filedAddressLabel(String countyId) {
		
		return RestResponse.success(cityMapper.queryAddressLabel(countyId));
	}

	@Override
	public RestResponse<City> filedCityById(String id) {
	
		return RestResponse.success(cityMapper.filedCityById(id));
	}


}
