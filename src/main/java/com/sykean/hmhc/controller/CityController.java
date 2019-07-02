package com.sykean.hmhc.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.City;
import com.sykean.hmhc.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("city")
@Validated
@Api(value = "城市接口", description = "城市接口")
public class CityController {

    @Autowired
    private CityService cityService;

    @ApiOperation(value = "根据层级查城市接口")
    @GetMapping("level")
    public RestResponse<List<City>> list(@RequestParam Integer level) {
    	
    	return cityService.filedListLevel(level);
    }
    
    @ApiOperation(value = "根据父id查城市接口")
    @GetMapping("parentCityId")
    public RestResponse<List<City>> filedListParentId(@RequestParam String parentCityId) {
    	
    	return  cityService.filedListParentId(parentCityId);
    }

}
