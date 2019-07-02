package com.sykean.hmhc.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.DictData;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;



public interface DictDataService {


	/**
	 * 新增
	 */
	RestResponse<Integer> save(DictDataReq dictDataReq ,User user);
	/**
	 * 根据id删除
	 */
	RestResponse<Integer>  deleteById(String ids);
	/**
	 * 修改
	 */
	RestResponse<Integer>  update(DictDataReq dictDataUpateReq,String userId);


	RestResponse<DictDataReq> findById(String id);

	RestResponse<PageRes<DictDataReq>> queryAll(DictDataReq dictDataSearch);

	RestResponse<Integer> findBykeyAndType(DictDataReq dictDataReq);
	
	RestResponse<String> findValByType(String type,String val);
	
	/**
	 * 根据数字典 type 查询是否存在该字典 没有的话 创建该字典
	 * @param type
	 * @param dictVal
	 * @return
	 */
	RestResponse<String> findDictId(String type,String dictVal);
	
	/**
	 * 查询民族的list
	 * @return
	 */
	RestResponse<List<DictData>> queryList();

	RestResponse<Integer> checkRepeatName(DictDataReq dictDataReq);
	
	/**
	 * 查询所有
	 * @param dictDataSearch
	 * @return
	 */
	RestResponse<List<DictDataReq>> findAll(DictDataReq dictDataSearch);
	
	/**
	 * //查询该类型总数
	 * @param dictDataReq
	 * @return
	 */
	RestResponse<List<DictDataReq>> findOrderNumSelects(@Valid DictDataReq dictDataReq);
	
	/**
	 * 根据type和order查询
	 * @param dictDataReq
	 * @return
	 */
	RestResponse<Integer> findByOrdetAndType(DictDataReq dictDataReq);
	
}
