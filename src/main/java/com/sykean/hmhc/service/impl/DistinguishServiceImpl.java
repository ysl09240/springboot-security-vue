package com.sykean.hmhc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.DictConstants;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.*;
import com.sykean.hmhc.mapper.DeptMapper;
import com.sykean.hmhc.entity.BusIrisRecord;
import com.sykean.hmhc.mapper.DictDataMapper;
import com.sykean.hmhc.mapper.DistinguishMapper;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.disinguish.DistinguishReq;
import com.sykean.hmhc.res.iris.*;
import com.sykean.hmhc.res.iris.DistinguishVo;
import com.sykean.hmhc.res.iris.IrisRecord;
import com.sykean.hmhc.res.iris.IrisRecordDetail;
import com.sykean.hmhc.res.iris.IrisRecordInfo;
import com.sykean.hmhc.service.CityService;
import com.sykean.hmhc.service.DistinguishService;
import com.sykean.hmhc.util.SysUtils;
import org.apache.commons.lang3.StringUtils;
import com.sykean.hmhc.util.DateUtil;
import com.sykean.hmhc.util.DictDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: 魏鑫辉
 * @Date: 2019/6/20 14:09
 * @Description:
 */
@Service
public class DistinguishServiceImpl implements DistinguishService {
    @Autowired
    DistinguishMapper distinguishMapper;
    @Autowired
    DictDataMapper dictDataMapper;
    @Autowired
    CityService cityService;

    @Autowired
    DeptMapper deptMapper;

    private static String dateStr = "";

    @Override
    public RestResponse<PageRes<IrisRecord>> getIrisRecordByParamater(DistinguishReq distinguishReq) {
        String province = "";
        String city = "";
        String county = "";
        if(StringUtils.isNotEmpty(distinguishReq.getProvinceId())){
            province=cityService.filedCityById(distinguishReq.getProvinceId()).getData().getCity();
        }
        if(StringUtils.isNotEmpty(distinguishReq.getCityId())){
            city=cityService.filedCityById(distinguishReq.getCityId()).getData().getCity();
        }
        if(StringUtils.isNotEmpty(distinguishReq.getCountyId())){
            county=cityService.filedCityById(distinguishReq.getCountyId()).getData().getCity();
        }
        String residenceAddr=province+city+county;
        if(StringUtils.isNotEmpty(residenceAddr)){
            distinguishReq.setResidenceAddr(residenceAddr+"%");
        }
        if(distinguishReq.getName()!=null && distinguishReq.getName()!=""){
            distinguishReq.setName("%"+distinguishReq.getName()+"%");
        }
        PageHelper.startPage(distinguishReq.getPageIndex(), distinguishReq.getPageSize());
        List<IrisRecord> irisRecordList = distinguishMapper.getIrisRecordByParamater(distinguishReq);
        long total = new PageInfo<>(irisRecordList).getTotal();
        if (total == 0) {
            return RestResponse.success(new PageRes<>(new ArrayList<>(0), 0));
        }
        //查询所有数据字典数据
        List<DictDataReq> reasonCollList = DictDataUtil.getDict(DictConstants.REASON_COLL);
        List<DictDataReq> nationList = DictDataUtil.getDict(DictConstants.NATION_TYPE);
        List<DictDataReq> cerTypeList = DictDataUtil.getDict(DictConstants.CERTIFICATES_TYPE);
        Map<String, DictDataReq> reasonCollMap = reasonCollList.stream().collect(Collectors.toMap(DictDataReq::getValue, a -> a, (k1, k2) -> k1));
        Map<String, DictDataReq> nationMap = nationList.stream().collect(Collectors.toMap(DictDataReq::getValue, a -> a, (k1, k2) -> k1));
        Map<String, DictDataReq> cerTypeMap = cerTypeList.stream().collect(Collectors.toMap(DictDataReq::getValue, a -> a, (k1, k2) -> k1));
        for (IrisRecord irisRecord:irisRecordList) {
            //设置证件
            if(cerTypeMap.containsKey(irisRecord.getCerType())){
                irisRecord.setCerType(cerTypeMap.get(irisRecord.getCerType()).getName());
            }
            //设置民族
            if(nationMap.containsKey(irisRecord.getNation())){
                irisRecord.setNation(nationMap.get(irisRecord.getNation()).getName());
            }
            //设置人员类别
            if(reasonCollMap.containsKey(irisRecord.getPersonnelType())){
                irisRecord.setPersonnelType(reasonCollMap.get(irisRecord.getPersonnelType()).getName());
            }

        }
        return RestResponse.success(new PageRes<>(irisRecordList,total));
    }

    @Override
    public RestResponse<IrisRecordDetail> getIrisRecordDetail(String id, int pageNo) {
        IrisRecordDetail irisRecordDetail = distinguishMapper.getIrisRecordDetailByWorkerId(id);
        if(irisRecordDetail==null){
            return RestResponse.error(ResponseCode.ERROR,null);
        }
        int startNo=(pageNo-1)*5;
        List<IrisRecordInfo> irisRecordInfoList = distinguishMapper.getIrisRecordInfo(irisRecordDetail.getIrId(), startNo);

        irisRecordDetail.setRecordList(irisRecordInfoList);
        try {
            irisRecordDetail.setAge(DateUtil.getAgeByBirth(irisRecordDetail.getBirthday()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设置数据字典
        //设置国家(国家暂时不做处理)
        //irisRecordDetail.setCountry(DictDataUtil.queryLabel(DictConstants.COUNTRY_AREA,irisRecordDetail.getCountry()));
        irisRecordDetail.setCerType(DictDataUtil.queryLabel(DictConstants.CERTIFICATES_TYPE,irisRecordDetail.getCerType()));
        irisRecordDetail.setNation(DictDataUtil.queryLabel(DictConstants.NATION_TYPE,irisRecordDetail.getNation()));
        irisRecordDetail.setGender(DictDataUtil.queryLabel(DictConstants.GENDER,irisRecordDetail.getGender()));
        //设置站点类型
        irisRecordDetail.setSiteType(DictDataUtil.queryLabel(DictConstants.SITE_TYPE,irisRecordDetail.getSiteType()));
        irisRecordDetail.setFlgWorker(DictDataUtil.queryLabel(DictConstants.FLG_WO,irisRecordDetail.getFlgWorker()));
        //设置采集原因
        irisRecordDetail.setCollReason(DictDataUtil.queryLabel(DictConstants.REASON_COLL,irisRecordDetail.getCollReason()));
        //从工具类获取识别设备数据字典
        List<DictDataReq> dict = DictDataUtil.getDict(DictConstants.DEV_TYPE);
        Map<String, DictDataReq> devMap = dict.stream().collect(Collectors.toMap(DictDataReq::getValue, a -> a, (k1, k2) -> k1));
        if(devMap.containsKey(irisRecordDetail.getDevType())){
            irisRecordDetail.setDevType(devMap.get(irisRecordDetail.getDevType()).getName());
        }
        List<IrisRecordInfo> recordList = irisRecordDetail.getRecordList();
        for (IrisRecordInfo irisRecordInfo:recordList) {
            if(devMap.containsKey(irisRecordInfo.getDevType())){
                irisRecordInfo.setDevType(devMap.get(irisRecordInfo.getDevType()).getName());
            }
        }
        return RestResponse.success(irisRecordDetail);
    }

    @Override
    public RestResponse<DistinguishVo> getDistinguishRecord(DistinguishReq distinguishReq, User user) {
        //1，根据虹膜查询虹膜信息和人员人息
        DistinguishVo distinguishVo = distinguishMapper.getDistinguishRecord(distinguishReq);
        if(null == distinguishVo ){
            return RestResponse.error(ResponseCode.ERROR,null);
        }
        try {
            distinguishVo.setAge(DateUtil.getAgeByBirth(distinguishVo.getSBirth()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Dept dept = deptMapper.selectById(user.getDeptId());
        //2，识别信息查询成功后，要将此次识别信息保存到库里记录，并返回到前端
        //2.1,保存身份识别信息（bus_cer_record）
        if("".equals(this.dateStr)){
            BusIrisRecord temp = distinguishMapper.getLastOneBusIrisRecords();
            if(null !=temp){
                dateStr = dept.getZzcode()+this.getCIID(temp.getCiId());
            }else{
                dateStr = dept.getZzcode()+DateUtil.DateToString(new Date(),0)+"0000000001";
            }
        }else{
            dateStr = dept.getZzcode()+this.getCIID(dateStr);
        }
        BusCerRecord busCerRecord = new BusCerRecord();
        busCerRecord.setId(SysUtils.GetUUID());
        busCerRecord.setSName(distinguishVo.getSName());
        busCerRecord.setSCountry(distinguishVo.getSCountry());
        busCerRecord.setSGender(distinguishVo.getSGender());
        busCerRecord.setSNation(distinguishVo.getSNation());
        busCerRecord.setCiId(dateStr);
        busCerRecord.setAddr1(distinguishVo.getAddr1());
        busCerRecord.setAddr1Code(distinguishVo.getAddr1Code());
        busCerRecord.setAddr2(distinguishVo.getAddr2());
        busCerRecord.setAddr2Code(distinguishVo.getAddr2Code());
        busCerRecord.setCerAuth(distinguishVo.getCerAuth());
        busCerRecord.setCerNo(distinguishVo.getCerNo());
        busCerRecord.setCerType(distinguishVo.getCerType());
        busCerRecord.setCerValid(distinguishVo.getCerValid());
        busCerRecord.setFId("");
        busCerRecord.setOpTeller(distinguishVo.getOpTeller());
        busCerRecord.setOpTime(distinguishVo.getOpTime());
        busCerRecord.setSBirth(distinguishVo.getSBirth());
        busCerRecord.setTel1(distinguishVo.getTel1());
        busCerRecord.setTel2(distinguishVo.getTel2());
        distinguishMapper.addBusCerRecord(busCerRecord);

        //2.2,保存虹膜采集识别记录（bus_iris_record）
        BusIrisRecord busIrisRecord = new BusIrisRecord();
        busIrisRecord.setId(SysUtils.GetUUID());
        busIrisRecord.setCiId(dateStr);//采集识别编码
        busIrisRecord.setSId(distinguishReq.getUserid());//人员编码
        busIrisRecord.setIrId(distinguishVo.getIrId());//虹膜id
        busIrisRecord.setStId(distinguishVo.getStId());//采集点id
        busIrisRecord.setAreaCode(user.getDeptId());
        busIrisRecord.setStX("");//采集坐标x
        busIrisRecord.setStY(""); //采集点坐标Y
        busIrisRecord.setDzmc(dept.getCodeName());//业务发生地点
        busIrisRecord.setSecLev(distinguishVo.getSecLev());//人员安全级别
        busIrisRecord.setOpTeller(user.getUsername());//操作人
        busIrisRecord.setOpTime(DateUtil.DateToString(new Date(),2));//操作时间
        busIrisRecord.setTimeColl("");//采集耗时
        busIrisRecord.setTimeJz("");//警综耗时
        busIrisRecord.setSiteType("");//采集点类型
        busIrisRecord.setDevType(distinguishVo.getDevType());//设备类型
        busIrisRecord.setCollName(distinguishVo.getCollName());
        busIrisRecord.setFlgAlarm("");
        busIrisRecord.setFlgEye(distinguishReq.getFlgEye());
        busIrisRecord.setFlgFocus(distinguishVo.getFlgFocus());
        busIrisRecord.setFlgWo(distinguishVo.getFlgWo());
        busIrisRecord.setCreateUserId(user.getId());
        busIrisRecord.setCreateTime(new Date());
        busIrisRecord.setUpdateUserId(user.getId());
        busIrisRecord.setUpdateTime(new Date());
        distinguishMapper.addBusIrisRecord(busIrisRecord);

        //报警原因
        BusAlarmRecord busAlarmRecord = new BusAlarmRecord();
        busAlarmRecord.setAreaCode(dept.getCode());
        busAlarmRecord.setCerType(distinguishVo.getCerType());
        busAlarmRecord.setCerNo(distinguishVo.getCerNo());
        busAlarmRecord.setOpTeller(user.getUsername());
        busAlarmRecord.setCiId(dateStr);
        busAlarmRecord.setRsnAlarm("无");
        busAlarmRecord.setRsnDesc("无");
        busAlarmRecord.setStId(distinguishVo.getStId());
        busAlarmRecord.setSName(distinguishVo.getSName());
        busAlarmRecord.setSCountry(distinguishVo.getSCountry());
        busAlarmRecord.setSGender(distinguishVo.getSGender());
        busAlarmRecord.setSNation(distinguishVo.getSNation());
        busAlarmRecord.setId(SysUtils.GetUUID());
        busAlarmRecord.setCreateUserId(user.getId());
        busAlarmRecord.setUpdateUserId(user.getId());
        busAlarmRecord.setCreateTime(new Date());
        busAlarmRecord.setUpdateTime(new Date());
        distinguishMapper.addBusAlarmRecord(busAlarmRecord);

        if(null == distinguishReq.getPageIndex()|| null == distinguishReq.getPageSize()){
            distinguishReq.setPageIndex(1);
            distinguishReq.setPageSize(5);
        }
        //继续查询识别记录以及报警信息
        PageHelper.startPage(distinguishReq.getPageIndex(), distinguishReq.getPageSize());
        List<BusIrisRecordVo> busIrisRecords = distinguishMapper.getBusIrisRecordsList(distinguishReq);
        if(busIrisRecords != null){
            PageInfo<BusIrisRecordVo> pageInfo = new PageInfo<>(busIrisRecords);
            distinguishVo.setBusIrisRecords(pageInfo);
        }
        return RestResponse.success(distinguishVo);
    }

    @Override
    public RestResponse<PageRes<IrisRecordInfo>> getIrisRecordInfo(String irId, int pageNo) {
        int startNo=(pageNo-1)*5;
        List<IrisRecordInfo> irisRecordInfoList = distinguishMapper.getIrisRecordInfo(irId, startNo);
        //从工具类获取识别设备数据字典
        List<DictDataReq> dict = DictDataUtil.getDict(DictConstants.DEV_TYPE);
        Map<String, DictDataReq> devMap = dict.stream().collect(Collectors.toMap(DictDataReq::getValue, a -> a, (k1, k2) -> k1));
        for (IrisRecordInfo irisRecordInfo:irisRecordInfoList) {
            if(devMap.containsKey(irisRecordInfo.getDevType())){
                irisRecordInfo.setDevType(devMap.get(irisRecordInfo.getDevType()).getName());
            }
        }
        int total = distinguishMapper.totalDistinguishRecord(irId, startNo);
        return RestResponse.success(new PageRes<>(irisRecordInfoList,total));
    }

    @Override
    public RestResponse<PageInfo<BusIrisRecordVo>> getBusIrisRecordsList(DistinguishReq distinguishReq) {

        //继续查询识别记录以及报警信息
        PageHelper.startPage(distinguishReq.getPageIndex(), distinguishReq.getPageSize());
        List<BusIrisRecordVo> busIrisRecords = distinguishMapper.getBusIrisRecordsList(distinguishReq);
        return RestResponse.success(new PageInfo<>(busIrisRecords));
    }

    private String getCIID(String str){
        if("".equals(str)){
            return "";
        }
        String newStr = "";
        String dTemp = str.substring(12,20);
        String nowTemp = DateUtil.DateToString(new Date(),0);
        try {
            if(DateUtil.differentDays(DateUtil.parseDate(dTemp,"yyyyMMdd"),DateUtil.parseDate(nowTemp,"yyyyMMdd"))==0){
                DecimalFormat countFormat = new DecimalFormat("0000000000");
                newStr = dTemp + countFormat.format(Integer.parseInt(str.substring(20))+1);
            }else{
                newStr = nowTemp+"0000000001";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newStr;
    }

}
