package com.sykean.hmhc.service;

import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.req.collect.WorkerIrisSaveReq;

public interface IrisInfoService {

	RestResponse save(WorkerIrisSaveReq workerIrisSaveReq,String workerIrisNum);
}
