package com.sykean.hmhc.service;
import java.util.List;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.City;
public interface CityService {

     /*
     *  根据层级查询
     *
     */
	RestResponse<List<City>> filedListLevel(Integer level );
	
	/**
	 * 根据地址父id获取下一级地址
	 * @param 
	 * @return
	 */
	RestResponse<List<City>> filedListParentId(String parentCityId); 
	

	/**
	 * 获取省市区中文
	 * @param countyId
	 * @return
	 */
	RestResponse<String> filedAddressLabel(String countyId);
	
	/**
	 * 根据ctiy_id获取地址信息
	 * @param level
	 * @return
	 */
	RestResponse<City> filedCityById(String id);
	
}
