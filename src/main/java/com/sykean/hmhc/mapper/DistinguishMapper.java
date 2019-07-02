package com.sykean.hmhc.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.BusAlarmRecord;
import com.sykean.hmhc.entity.BusCerRecord;
import com.sykean.hmhc.entity.BusIrisRecord;
import com.sykean.hmhc.req.disinguish.DistinguishReq;
import com.sykean.hmhc.res.iris.*;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistinguishMapper extends BaseMapper<IrisRecord> {
    /**
     * 根据条件查询识别记录
     * @param distinguishReq
     * @return
     */
    List<IrisRecord> getIrisRecordByParamater(DistinguishReq distinguishReq);

    int totalRecordByParamater(DistinguishReq distinguishReq);

    /**
     * 根据识别记录id查询虹膜识别记录详情
     */
    IrisRecordDetail getIrisRecordDetailByWorkerId(String id);

    /**
     * 根据识别记录id，和pageNo查询五条虹膜识别记录
     */
    List<IrisRecordInfo> getIrisRecordInfo(@Param("irId") String irId, @Param("startNo") int startNo);

    int totalDistinguishRecord(@Param("irId") String irId, @Param("startNo") int startNo);

    /**
     * 查询识别信息
     * @param distinguishReq
     * @return
     */
    DistinguishVo getDistinguishRecord(DistinguishReq distinguishReq);

    /**
     * 根据虹膜信息查询识别记录
     * @param distinguishReq
     * @return
     */
    List<BusIrisRecordVo> getBusIrisRecordsList(DistinguishReq distinguishReq);

    /**
     * 添加身份信息采集识别信息
     * @param busCerRecord
     */
    void addBusCerRecord(BusCerRecord busCerRecord);

    /**
     * 添加识别记录
     * @param busIrisRecord
     */
    void addBusIrisRecord(BusIrisRecord busIrisRecord);

    BusIrisRecord getLastOneBusIrisRecords();

    /**
     * 添加报警信息
     * @param busAlarmRecord
     */
    void addBusAlarmRecord(BusAlarmRecord busAlarmRecord);
}