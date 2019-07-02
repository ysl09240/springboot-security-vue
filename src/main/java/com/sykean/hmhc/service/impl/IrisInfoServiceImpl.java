package com.sykean.hmhc.service.impl;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.IrisInfo;
import com.sykean.hmhc.mapper.IrisInfoMapper;
import com.sykean.hmhc.req.collect.WorkerIrisSaveReq;
import com.sykean.hmhc.service.IrisInfoService;
import com.sykean.hmhc.util.BeanUtils;
import com.sykean.hmhc.util.SecurityUtil;
import com.sykean.hmhc.util.SysUtils;

/**
 * @author pu.zhao
 * @description
 * @date 2019/6/22
 */
@Service
public class IrisInfoServiceImpl implements IrisInfoService {


   @Autowired
   private IrisInfoMapper irisInfoMapper;


	@Override
	public RestResponse save(@Valid WorkerIrisSaveReq workerIrisSaveReq,String workerIrisNum) {
			IrisInfo irisInfo = new IrisInfo();
			Date date =  new Date();
	        //复制请求对象字段
	        BeanUtils.copyProperties(workerIrisSaveReq, irisInfo);
	        irisInfo.setId(SysUtils.GetUUID());
	        //创建时间
	        irisInfo.setCreateTime(date);
	        //创建人id
	        irisInfo.setCreateUserId(SecurityUtil.getUser().getId());
	        irisInfo.setSId(workerIrisNum);
	        irisInfo.setIrId(workerIrisNum);
	        irisInfoMapper.insert(irisInfo);
	        return RestResponse.success();
	}



}
