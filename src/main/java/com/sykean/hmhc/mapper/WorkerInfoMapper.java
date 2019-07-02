package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.WorkerInfo;
import com.sykean.hmhc.req.collect.CollectSearchReq;
import com.sykean.hmhc.res.collect.CollectDetailRes;
import com.sykean.hmhc.res.collect.CollectListRes;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WorkerInfoMapper extends BaseMapper<WorkerInfo> {

    List<CollectListRes> findCollectList(CollectSearchReq collectSearchReq);

    CollectDetailRes findById(String id);

	int createTable(@Param("tableName") String tableName);
    
    WorkerInfo findByCerNo(@Param("cerNo")  String cerNo);

	String getMaxCode(@Param("code") String code);
}
