package com.sykean.hmhc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.DictData;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;

public interface DictDataMapper extends BaseMapper<DictData> {
	//同一字段类型字段值不能重复
    int findBykeyAndType(DictDataReq dictDataReq);

    String findValByType(@Param("type") String type,@Param("val")  String val);

    //查询所有民族
    List<DictData> queryList();
    //同一字段类型字段名称不能重复
    int checkRepeatName(DictDataReq dictDataReq);

    DictDataReq selectByPrimaryKey(@Param("id") String id);

    List<DictDataReq> queryAll (DictDataReq dictDataSearch);
    //同一字段类型的总数
    int findOrderNumSelects(DictDataReq dictDataReq);

    //查询该类型的序号是否存在
    int findByOrdetAndType(DictDataReq dictDataReq);
    
	int batchUpdateDesc(@Param("list") List<Integer> list,@Param("type") String type);
	
	int batchUpdateAsc(@Param("list") List<Integer> list,@Param("type") String type);
	
}
