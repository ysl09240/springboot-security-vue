package com.sykean.hmhc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.service.DictDataService;
@Component
public class DictDataUtil {
   
	
	@Autowired
	private DictDataService dictDataService;
	
	public static DictDataService ddFacade ;
    private static RedisTemplate<Object,Object> redisTemp;
	
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
		
	private static Map<String,List<DictDataReq>> dictMap = new ConcurrentHashMap<String,List<DictDataReq>>();
	private static Map<String,String> redisMap = new ConcurrentHashMap<String,String>();
	
	@PostConstruct
	public void init() {
		ddFacade=dictDataService;
		redisTemp=redisTemplate;
		getALLDict();
	}
	
	
	public static List<DictDataReq>  getDict(String typeName) {
		
		if(dictMap==null || dictMap.size()==0) {
			getALLDict();
		 }
		
		return dictMap.get(typeName);
	}

	public static void getALLDict() {
		/*if(ddFacade==null) {
			ddFacade=(DictDataService) SpringContextUtil.getBean("dictDataServiceImpl");
		}*/
		redisMap.clear();
		
		DictDataReq t =new DictDataReq();
		//t.setType("60");
		RestResponse<List<DictDataReq>> tModel=ddFacade.findAll(t);
		if(tModel.isSuccess() && tModel.getData()!=null) {
			for(DictDataReq model:tModel.getData()) {
				dealMap(model);
			}
		}
		
		for(String key:dictMap.keySet()) {
			redisMap.put(key, JsonMapper.toJson(dictMap.get(key)));
		}
		if(redisMap!=null && !redisMap.isEmpty()) {
			redisTemp.opsForValue().set("dict:map", redisMap);
		}
		
	}
	private static void dealMap(DictDataReq model) {
		List<DictDataReq> list=null;
		if(dictMap.containsKey(model.getType())) {
			list= dictMap.get(model.getType());
			list.add(model);
			dictMap.put(model.getType(), list);
		}else {
			list =new ArrayList<DictDataReq>();
			list.add(model);
			dictMap.put(model.getType(), list);
		}
	}
	
	public static void clear() {
		dictMap.clear();
		redisTemp.delete("dict:map");
	
		getALLDict();
	}
	
	
	public static String queryLabel(String typeName,String value) {
		List<DictDataReq> list =getDict(typeName);
		return queryLabel(list,value);
	}
	
	public static String queryLabel(List<DictDataReq> list,String value) {
		if(StringUtils.isEmpty(value)) {
			return "";
		}
		if(CollectionUtils.isEmpty(list) ) {
			return value;
		}
		
		for(DictDataReq view:list) {
			if(StringUtils.equals(view.getValue(),value)) {
				return view.getName();
			}
		}
		return "";
	}
	
	
}
