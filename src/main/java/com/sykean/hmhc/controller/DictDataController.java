package com.sykean.hmhc.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;
import com.sykean.hmhc.service.DictDataService;
import com.sykean.hmhc.util.DictDataUtil;
import com.sykean.hmhc.util.SecurityUtil;

@RestController
@RequestMapping("dictData")
@Validated
@Api(value = "数据字典接口", description = "数据字典接口")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @ApiOperation(value = "数据字典列表")
    @PostMapping("list")
    public RestResponse<PageRes<DictDataReq>> list(@RequestBody @Valid DictDataReq dictDataSearch) {
    	
    	return dictDataService.queryAll(dictDataSearch);
    }
    
    @ApiOperation(value = "新增数据字典")	
    @PostMapping("save")
    public RestResponse save(@RequestBody @Valid DictDataReq dictDataReq) {
    	User user = SecurityUtil.getUser();
    	RestResponse<Integer> result = null;
		//校验重复 同级别不能重复的key
		result = dictDataService.checkRepeatName(dictDataReq);//3
		if(result.getData() > 0){
			return  RestResponse.success(result.getData());
		}
		result = dictDataService.findBykeyAndType(dictDataReq);//2
		if(result.getData() > 0){
			return  RestResponse.success(result.getData());
		}else{
			result = dictDataService.save(dictDataReq , user);
			//清除字典缓存
			DictDataUtil.clear();
			return RestResponse.success(result);
		}
    }
    
    @ApiOperation(value = "修改数据字典")
    @PostMapping("update")
    public RestResponse<?> update(@RequestBody @Valid DictDataReq dictDataUpateReq) {
    	User user = SecurityUtil.getUser();
        return dictDataService.update(dictDataUpateReq ,user.getId());
    }
    
    @ApiOperation(value = "根据id查询数据字典")
    @GetMapping("find")
    public RestResponse<DictDataReq> findById(@RequestParam("id") String id) {
        return dictDataService.findById(id);
    }
   
    @ApiOperation(value = "删除数据字典")
    @GetMapping("del")
    public RestResponse<Integer> delete(@RequestParam("dictIds") String dictIds) {
    	User usermodel = SecurityUtil.getUser();
    	List<String> idL = Arrays.asList(dictIds);
		JSONObject json = new JSONObject();
		int sCount = 0;
		int eCount = 0;
		RestResponse<Integer> tempRes =null;
		for (String id : idL) {
			RestResponse<DictDataReq> res = null;
		    String [] jsonist=id.split(",");		
			for (int i = 0; i < jsonist.length; i++) {    
				res = dictDataService.findById(jsonist[i]);
				if(res.isSuccess()){
					DictDataReq tem = res.getData();
					tem.setDelFlag((byte)1);
					tempRes = dictDataService.update(tem,usermodel.getId());
					if(tempRes.isSuccess()){
						sCount++;
						json.put(tem.getName(), tempRes.getData());
					}else{
						eCount++;
					}
				}else{
					eCount++;
				}
			}
		}
		json.put("eCount", eCount);
		json.put("sCount", sCount);
		//清除字典缓存
		DictDataUtil.clear();
		return RestResponse.success();
    }
    
    @ApiOperation(value = "判断name是否重复")
    @PostMapping("findName")
    public RestResponse<?> checkRepeatName(@RequestBody @Valid DictDataReq dictDataReq) {
        return dictDataService.checkRepeatName(dictDataReq);
    }
    
    @ApiOperation(value = "判断type下value是否重复")
    @PostMapping("findValue")
    public RestResponse<?> findBykeyAndType(@RequestBody @Valid DictDataReq dictDataReq) {
        return dictDataService.findBykeyAndType(dictDataReq);
    }
    
    @ApiOperation(value = "获取多个字段值")
    @GetMapping("queryByTypes")
	public RestResponse<Map<String,List<DictDataReq>>> queryByTypes(@RequestParam String type){
		Map<String,List<DictDataReq>> returnMap =new HashMap<String,List<DictDataReq>>();
		if(StringUtils.isNotBlank(type)) {
			String[]  types= type.split(",");
			for(String t:types) {
				returnMap.put(t, DictDataUtil.getDict(t));
			}
		}
		return RestResponse.success(returnMap);
	}
    
    @ApiOperation(value = "获取字段类列表")
    @PostMapping("queryByModel")
	public RestResponse<List<DictDataReq>> queryByModel(@RequestBody @Valid DictDataReq t){
    	RestResponse<List<DictDataReq>> findOrderNumSelects = dictDataService.findOrderNumSelects(t);
    	List<DictDataReq> data = findOrderNumSelects.getData();
    	t.setList(data);
		return dictDataService.findAll(t);
	}
    
    @ApiOperation(value = "获取顺序号")
    @PostMapping("findOrderNumSelects")
    public RestResponse<List<DictDataReq>> findOrderNumSelects(@RequestBody @Valid DictDataReq dictDataReq) {
        return dictDataService.findOrderNumSelects(dictDataReq);
    }
}
