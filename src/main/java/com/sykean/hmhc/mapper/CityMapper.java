package com.sykean.hmhc.mapper;
import java.util.List;

import com.sykean.hmhc.res.city.CityVO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.City;
import org.springframework.cache.annotation.Cacheable;

public interface CityMapper extends  BaseMapper<City>{
	
	/*
	 * 根据层级查询城市
	 * 
	 */
	List<City> filedListLevel(@Param("level") Integer level);
	
	/**
	 * 根据地址父id获取下一级地址
	 * @param 
	 * @return
	 */
	List<City> filedListParentId(@Param("parentCityId") String parentCityId);
	
	/**
	 * 获取省市区中文
	 * @param countyId
	 * @return
	 */
	String queryAddressLabel(@Param("countyId") String countyId);
	
	/**
	 * 根据ctiy_id获取地址信息
	 * @param level
	 * @return
	 */
	City filedCityById (@Param("cityId") String cityId);

	/**
	 * @description  查询所有城市区
	 * @author litong
	 * @date 2019/6/28
	 */
	@Cacheable(value = "city")
	List<CityVO> findAll();
	
}