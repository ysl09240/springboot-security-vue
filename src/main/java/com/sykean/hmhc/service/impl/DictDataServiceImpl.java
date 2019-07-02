package com.sykean.hmhc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.DictData;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.mapper.DictDataMapper;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;
import com.sykean.hmhc.service.DictDataService;
import com.sykean.hmhc.util.BeanUtils;
import com.sykean.hmhc.util.SysUtils;



@Service
public class DictDataServiceImpl implements DictDataService {

	@Autowired
	private DictDataMapper dictDataMapper;

	/**
	 * 新增
	 */
	@Override
	public RestResponse<Integer> save(DictDataReq dictDataReq ,User user) {
		DictData dictData = BeanUtils.copyPropertiesByClass(dictDataReq, DictData.class);
		int ordetAndType = dictDataMapper.findByOrdetAndType(dictDataReq);//查询该类型的序号是否存在
		if(StringUtils.isNotEmpty(dictDataReq.getId())){
			if(ordetAndType>0){
				int orderOld = dictDataMapper.selectByPrimaryKey(dictDataReq.getId()).getOrderNum();
				Integer orderNow = dictDataReq.getOrderNum();
				if(orderOld < orderNow){
					orderOld = orderNow+1 ;
					orderNow = dictDataMapper.selectByPrimaryKey(dictDataReq.getId()).getOrderNum()+1;//取中间值
					List<Integer> list = new ArrayList<Integer>();
					for (int i = orderNow; i <orderOld; i++) {
						list.add(i);
					}
					dictDataMapper.batchUpdateAsc(list,dictDataReq.getType());//默认从小到大排序
				}else{
					List<Integer> list = new ArrayList<Integer>();
					for (int i = orderNow; i <orderOld; i++) {
						list.add(i);
					}
					Collections.sort(list,Collections.reverseOrder());//从大到小排序
					dictDataMapper.batchUpdateDesc(list,dictDataReq.getType());
				}
			}
			dictData.setUpdateUserId(user.getId());
			dictData.setUpdateTime(new Date());
			dictDataMapper.updateById(dictData);
		}else{
			if(ordetAndType>0){
				int orderMax = dictDataMapper.findOrderNumSelects(dictDataReq);//查询该类型总数
				List<Integer> list = new ArrayList<Integer>();
				for (int i = dictDataReq.getOrderNum(); i <orderMax+1; i++) {
					list.add(i);
				}
				Collections.sort(list,Collections.reverseOrder());//从大到小排序
				dictDataMapper.batchUpdateDesc(list,dictDataReq.getType());
			}
			dictData.setCreateUserId(user.getId());
			dictData.setCreateTime(new Date());	
			dictData.setDelFlag((byte) 0);
			dictData.setId(SysUtils.GetUUID());
			dictDataMapper.insert(dictData);
		}
		return RestResponse.success ();
	}
	@Override
	public RestResponse<Integer> deleteById(String ids) {
		return RestResponse.success (dictDataMapper.deleteById(ids));
	}
	@Override
	public RestResponse<Integer> update(DictDataReq dictDataUpateReq,String userId) {
		
		DictData dictData = BeanUtils.copyPropertiesByClass(dictDataUpateReq, DictData.class);
		dictData.setUpdateUserId(userId);
		dictData.setUpdateTime(new Date());
		return RestResponse.success (dictDataMapper.updateById(dictData));
	}

	@Override
	public RestResponse<DictDataReq> findById(String id) {
		DictDataReq dictData = dictDataMapper.selectByPrimaryKey(id);
		return (RestResponse<DictDataReq>) RestResponse.success (dictData);
	}

	@Override
	public RestResponse<PageRes<DictDataReq>> queryAll(DictDataReq dictDataSearch) {
		PageHelper.startPage(dictDataSearch.getPageIndex(), dictDataSearch.getPageSize());
		List<DictDataReq> weekPlans = dictDataMapper.queryAll(dictDataSearch);
		for (DictDataReq vdd : weekPlans) {
			if("60".equals(vdd.getType())){
				vdd.setTypeName("字典类型");
			}
		}
		 long total = new PageInfo<>(weekPlans).getTotal(); 
		return RestResponse.success(new PageRes<>(weekPlans, total));
	}

	@Override
	public RestResponse<Integer> findBykeyAndType(DictDataReq dictDataReq) {
		int findBykeyAndType = dictDataMapper.findBykeyAndType(dictDataReq);
		if(findBykeyAndType>0){
			findBykeyAndType=2;
		}
		return RestResponse.success (findBykeyAndType);
	}

	@Override
	public RestResponse<String> findValByType(String type, String val) {
		return RestResponse.success (dictDataMapper.findValByType(type , val));
	}

	@Override
	public RestResponse<String> findDictId(String type, String val) {
		return null;
	}

	@Override
	public RestResponse<List<DictData>> queryList() {
		return RestResponse.success (dictDataMapper.queryList());
	}

	@Override
	public RestResponse<Integer> checkRepeatName(DictDataReq dictDataReq) {
		int checkRepeatName = dictDataMapper.checkRepeatName(dictDataReq);
		if(checkRepeatName>0){
			checkRepeatName=3;
		}
		return RestResponse.success (checkRepeatName);
	}
	
	@Override
	public RestResponse<List<DictDataReq>> findAll(DictDataReq dictDataSearch) {
		
		List<DictDataReq> weekPlans = dictDataMapper.queryAll(dictDataSearch);
		for (DictDataReq dictDataReq : weekPlans) {
			dictDataReq.setList(dictDataSearch.getList());
		}
		return RestResponse.success( weekPlans); 
	}

	/**
     * 构建计划查询条件
     *
     */
	private Wrapper<DictData> generationCondition(DictDataSearch dictDataSearch) {
        Wrapper<DictData> wrapper = new EntityWrapper<DictData>();
        if(StringUtils.isNotBlank(dictDataSearch.getName())){
        	 wrapper.like("name", dictDataSearch.getName());
        }
        if(StringUtils.isNotBlank(dictDataSearch.getValue())){
        	wrapper.eq("value", dictDataSearch.getValue());
        }
        if(StringUtils.isNotBlank(dictDataSearch.getType())){
        	wrapper.eq("type", dictDataSearch.getType());
        }
        wrapper.orderBy("create_time", false);
        return wrapper;
    }
	@Override
	public RestResponse<List<DictDataReq>> findOrderNumSelects(@Valid DictDataReq dictDataReq) {
		int orderSel = dictDataMapper.findOrderNumSelects(dictDataReq);//查询该类型总数
		if(StringUtils.isEmpty(dictDataReq.getId())){
			orderSel = orderSel+1;
		}
		ArrayList<DictDataReq> list = new ArrayList<DictDataReq>();
		for (int i = 1; i <=orderSel; i++) {
			DictDataReq d = new DictDataReq();
			d.setOrderNum(i);
			list.add(d);
		}
		Collections.sort(list,Collections.reverseOrder());//从大到小排序
		return RestResponse.success (list);
	}
	@Override
	public RestResponse<Integer> findByOrdetAndType(DictDataReq dictDataReq) {
		return RestResponse.success (dictDataMapper.findByOrdetAndType(dictDataReq));
	}

}
