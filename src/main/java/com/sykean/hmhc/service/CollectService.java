package com.sykean.hmhc.service;

import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.WorkerInfo;
import com.sykean.hmhc.req.collect.CollectSearchReq;
import com.sykean.hmhc.req.collect.CollectUpdateReq;
import com.sykean.hmhc.req.collect.WorkerIrisSaveReq;
import com.sykean.hmhc.req.dept.DeptSaveReq;
import com.sykean.hmhc.req.user.UserListReq;
import com.sykean.hmhc.res.collect.CollectDetailRes;
import com.sykean.hmhc.res.collect.CollectListRes;
import com.sykean.hmhc.res.user.UserListRes;

import java.text.ParseException;

public interface CollectService {

    /**
     * @description
     * @author litong
     * @date 2019/6/20
     * @param collectSearchReq 采集信息列表查询请求体
     */
    RestResponse<PageRes<CollectListRes>> findList(CollectSearchReq collectSearchReq);

    /**
     * @description 根据人员id查询人员详情
     * @author litong
     * @date 2019/6/26
     * @param id
     */
    RestResponse<CollectDetailRes> findById(String id) throws ParseException;
    
    /**
     * @param workerIrisSaveReq 采集信息新增请求体
     * @return com.sykean.hmhc.common.RestResponse
     * @description 新增采集信息
     * @author litong
     * @date 2019-03-07
     */
	RestResponse save(WorkerIrisSaveReq workerIrisSaveReq);
	/**
	 * @description 修改采集信息
	 * @author litong
	 * @date 2019/6/26
	 * @param collectUpdateReq 采集信息编辑请求体
	 */
	RestResponse update(CollectUpdateReq collectUpdateReq);

	RestResponse<WorkerInfo> checkByCerNo(String cerNo);
}
