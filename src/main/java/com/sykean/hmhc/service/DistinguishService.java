package com.sykean.hmhc.service;

import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.req.disinguish.DistinguishReq;
import com.sykean.hmhc.res.iris.BusIrisRecordVo;
import com.sykean.hmhc.res.iris.DistinguishVo;
import com.sykean.hmhc.res.iris.IrisRecord;
import com.sykean.hmhc.res.iris.IrisRecordDetail;
import com.sykean.hmhc.res.iris.IrisRecordInfo;

public interface DistinguishService {
  RestResponse<PageRes<IrisRecord>> getIrisRecordByParamater(DistinguishReq distinguishReq);

  /**
   * 根据id获取识别记录详情
   * @param id
   * @param startNo
   * @return
   */
  RestResponse<IrisRecordDetail> getIrisRecordDetail(String id, int startNo);

  RestResponse<DistinguishVo> getDistinguishRecord(DistinguishReq distinguishReq, User user);

  RestResponse<PageInfo<BusIrisRecordVo>> getBusIrisRecordsList(DistinguishReq distinguishReq);

  RestResponse<PageRes<IrisRecordInfo>> getIrisRecordInfo(String sId,int pageSize);
}
