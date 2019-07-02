package com.sykean.hmhc.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.IrisInfo;

public interface IrisInfoMapper extends BaseMapper<IrisInfo> {

	int createTable(@Param("tableName") String tableName);
}
