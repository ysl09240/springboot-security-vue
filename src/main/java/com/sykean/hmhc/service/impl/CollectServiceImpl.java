package com.sykean.hmhc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.sykean.hmhc.res.city.CityVO;
import com.sykean.hmhc.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.DictConstants;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.config.fastdfs.core.FastDFSTemplate;
import com.sykean.hmhc.config.fastdfs.properties.FastDFSProperties;
import com.sykean.hmhc.entity.City;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.entity.DictData;
import com.sykean.hmhc.entity.IrisInfo;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.entity.WorkerInfo;
import com.sykean.hmhc.mapper.CityMapper;
import com.sykean.hmhc.mapper.DeptMapper;
import com.sykean.hmhc.mapper.DictDataMapper;
import com.sykean.hmhc.mapper.IrisInfoMapper;
import com.sykean.hmhc.mapper.UserMapper;
import com.sykean.hmhc.mapper.WorkerInfoMapper;
import com.sykean.hmhc.req.collect.CollectSearchReq;
import com.sykean.hmhc.req.collect.CollectUpdateReq;
import com.sykean.hmhc.req.collect.WorkerIrisSaveReq;
import com.sykean.hmhc.res.collect.CollectDetailRes;
import com.sykean.hmhc.res.collect.CollectListRes;
import com.sykean.hmhc.service.CollectService;
import com.sykean.hmhc.service.IrisInfoService;

/**
 * @author litong
 * @description
 * @date 2019/6/19
 */
@Service
public class CollectServiceImpl implements CollectService {
	
	private static Logger logger = LoggerFactory.getLogger(CollectServiceImpl.class);


   @Autowired
   private WorkerInfoMapper workerInfoMapper;

   @Autowired
   private IrisInfoMapper irisInfoMapper;

   @Autowired
   private DeptMapper deptMapper;

   @Autowired
   private IrisInfoService irisInfoService;

   @Autowired
   private FastDFSTemplate fastDFSTemplate;
   
   @Autowired
   private FastDFSProperties fastDFSProperties;

   @Autowired
   private UserMapper userMapper;

   @Autowired
   private DictDataMapper dictDataMapper;
   
   @Value("${irisserverurl.irisurl}")
   private String irisServerUrl;

   @Autowired
   private CityMapper cityMapper;


	@Override
	public RestResponse<PageRes<CollectListRes>> findList(CollectSearchReq collectSearchReq) {
		if(StringUtils.isNotBlank(collectSearchReq.getCityId()) ||
				StringUtils.isNotBlank(collectSearchReq.getCountyId()) ||
				StringUtils.isNotBlank(collectSearchReq.getProvinceId())){
			//查询所有城市（省市区）放入map 对查询条件 省市区进行判断
			List<CityVO> cityList = cityMapper.findAll();
			if (CollectionUtils.isNotEmpty(cityList)) {
				Map<String, String> cityMap = cityList.stream().collect(Collectors.toMap(CityVO::getCityId,CityVO::getCity));
				//省
				if (StringUtils.isNotBlank(collectSearchReq.getProvinceId()) && cityMap.containsKey(collectSearchReq.getProvinceId())) {
					String provinceId = collectSearchReq.getProvinceId();
					collectSearchReq.setProvinceName(cityMap.get(provinceId));
					collectSearchReq.setProvinceId(provinceId.substring(0,2));
				}
				//市
				if (StringUtils.isNotBlank(collectSearchReq.getCityId()) && cityMap.containsKey(collectSearchReq.getCityId())) {
					String cityId = collectSearchReq.getCityId();
					collectSearchReq.setCityName(cityMap.get(cityId));
					collectSearchReq.setCityId(cityId.substring(0,4));
					collectSearchReq.setProvinceId("");
					collectSearchReq.setProvinceName("");
				}
				//区
				if (StringUtils.isNotBlank(collectSearchReq.getCountyId()) && cityMap.containsKey(collectSearchReq.getCountyId())) {
					collectSearchReq.setCountyName(cityMap.get(collectSearchReq.getCountyId()));
					collectSearchReq.setCityName("");
					collectSearchReq.setCityId("");
					collectSearchReq.setProvinceId("");
					collectSearchReq.setProvinceName("");
				}
			}
		}
		PageHelper.startPage(collectSearchReq.getPageIndex(), collectSearchReq.getPageSize());
		//查询采集信息数据
		List<CollectListRes> collectListResList = workerInfoMapper.findCollectList(collectSearchReq);
		long total = new PageInfo<>(collectListResList).getTotal();
		if (total == 0) {
			return RestResponse.success(new PageRes<>(new ArrayList<>(0), 0));
		}
		//设置采集信息列表中需要显示名称的字段
		setNameForCollectList(collectListResList);
		return RestResponse.success(new PageRes<>(collectListResList, total));
	}
    /**
     * @description  处理采集信息列表中需要显示名称的字段
     * @author litong
     * @date 2019/6/29
     * @param collectList
     * @return void
     */
	private void setNameForCollectList(List<CollectListRes> collectList){
		//查询所有启用组织(采集点)
		List<Dept> deptList = deptMapper.selectList(new EntityWrapper<Dept>().eq("invalid", "1"));
		//查询所有用户信息
		List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("state", "1"));
		//查询所有数据字典数据
		List<DictData> dictDataList = dictDataMapper.selectList(new EntityWrapper<DictData>().eq("del_flag", "0"));
		Map<String, String> deptMap = new HashMap<>();
		Map<String, String> userMap = new HashMap<>();
		Map<String, String> nationMap = new HashMap<>();
		Map<String, String> cerTypeMap = new HashMap<>();
		Map<String, String> reasonCollMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(deptList)) {
			deptMap = deptList.stream().collect(Collectors.toMap(Dept::getCode,Dept::getCodeName));
		}
		if (CollectionUtils.isNotEmpty(userList)) {
			userMap = userList.stream().collect(Collectors.toMap(User::getId,User::getRealName));
		}
		if (CollectionUtils.isNotEmpty(dictDataList)) {
			for (DictData dictData : dictDataList) {
				if (DictConstants.REASON_COLL.equals(dictData.getType())) {
					reasonCollMap.put(dictData.getValue(), dictData.getName());
				}
				if (DictConstants.NATION_TYPE.equals(dictData.getType())) {
					nationMap.put(dictData.getValue(), dictData.getName());
				}
				if (DictConstants.CERTIFICATES_TYPE.equals(dictData.getType())) {
					cerTypeMap.put(dictData.getValue(), dictData.getName());
				}
			}

		}
		for (CollectListRes collectListRes : collectList) {
			//设置组织名称（采集点）
			if (deptMap.containsKey(collectListRes.getStId())) {
				collectListRes.setStName(deptMap.get(collectListRes.getStId()));
			}
			//设置采集人姓名（创建人姓名）
			if (userMap.containsKey(collectListRes.getCreateUserId())) {
				collectListRes.setCreateUserName(userMap.get(collectListRes.getCreateUserId()));
			}
			//设置采集原因
			if (reasonCollMap.containsKey(collectListRes.getReasonColl())) {
				collectListRes.setReasonCollName(reasonCollMap.get(collectListRes.getReasonColl()));
			}
			//设置民族
			if (nationMap.containsKey(collectListRes.getSNation())) {
				collectListRes.setSNationName(nationMap.get(collectListRes.getSNation()));
			}
			//设置证件类型
			if(cerTypeMap.containsKey(collectListRes.getCerType())){
				collectListRes.setCerTypeName(cerTypeMap.get(collectListRes.getCerType()));
			}
		}
	}

	@Override
	public RestResponse<CollectDetailRes> findById(String id) throws ParseException {
		CollectDetailRes collectDetailRes = workerInfoMapper.findById(id);
		//处理临时关注结束日期
		String focusEndDate = collectDetailRes.getFocusEndDate();
		if (StringUtils.isNotBlank(focusEndDate)) {
			focusEndDate = DateUtil.DateToString(new SimpleDateFormat("yyyyMMdd").parse(focusEndDate), 1);
			collectDetailRes.setFocusEndDate(focusEndDate);
		}
		//年龄
		if (collectDetailRes.getSBirth() != null) {
			collectDetailRes.setAge(DateUtil.getAgeByBirth(collectDetailRes.getSBirth()));
		}
		//站点坐标
		if (collectDetailRes.getLatitude() != null && collectDetailRes.getLongitude() != null) {
			collectDetailRes.setStCoordinate(String.valueOf(collectDetailRes.getLongitude()) + "," + String.valueOf(collectDetailRes.getLatitude()));
		}
		//采集原因
		if(StringUtils.isNotBlank(collectDetailRes.getReasonColl())){
			collectDetailRes.setReasonCollName(DictDataUtil.queryLabel(DictConstants.REASON_COLL,collectDetailRes.getReasonColl()));
		}
		//设备类型
		if(StringUtils.isNotBlank(collectDetailRes.getDevType())){
			collectDetailRes.setDevTypeName(DictDataUtil.queryLabel(DictConstants.DEV_TYPE,collectDetailRes.getDevType()));
		}
		//站点类型
		if(StringUtils.isNotBlank(collectDetailRes.getStType())){
			collectDetailRes.setStTypeName(DictDataUtil.queryLabel(DictConstants.SITE_TYPE,collectDetailRes.getStType()));
		}
		//性别
		if(StringUtils.isNotBlank(collectDetailRes.getSGender())){
			collectDetailRes.setSGender(DictDataUtil.queryLabel(DictConstants.GENDER,collectDetailRes.getSGender()));
		}
		//民族
		if(StringUtils.isNotBlank(collectDetailRes.getSNation())){
			collectDetailRes.setSNationName(DictDataUtil.queryLabel(DictConstants.NATION_TYPE,collectDetailRes.getSNation()));
		}
		//证件类型
		if(StringUtils.isNotBlank(collectDetailRes.getCerType())){
			collectDetailRes.setCerTypeName(DictDataUtil.queryLabel(DictConstants.CERTIFICATES_TYPE,collectDetailRes.getCerType()));
		}
		return RestResponse.success(collectDetailRes);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RestResponse save(WorkerIrisSaveReq workerIrisSaveReq) {
			WorkerInfo workerInfo = new WorkerInfo();
			Date date =  new Date();

			//去重判断（身份证）
			WorkerInfo findByCerNo = workerInfoMapper.findByCerNo(workerIrisSaveReq.getCerNo());
			if(findByCerNo != null){
				return RestResponse.error(ResponseCode.WORKER_NOT_EXISTS, null);
			}

			//滤重（比对省、部两级虹膜系统进行数据滤重）
			RestResponse<String> charComparison2 = charComparison(workerIrisSaveReq);
			if(!charComparison2.isSuccess()){//与虹膜库比对重复
				return charComparison2;
			}
			//上传文件
			uploadFile(workerIrisSaveReq);
	        //复制请求对象字段
	        BeanUtils.copyProperties(workerIrisSaveReq, workerInfo);
	        workerInfo.setId(SysUtils.GetUUID());
	        workerInfo.setFocusEndDate(!StringUtils.isEmpty(workerInfo.getFocusEndDate())?workerInfo.getFocusEndDate().replaceAll("-", ""):"");
	        //创建时间
	        workerInfo.setCreateTime(date);
	        //创建人id
	        workerInfo.setCreateUserId(SecurityUtil.getUser().getId());
	        RestResponse<String> workerIrisNum = getWorkerIrisNum();
	        if(!workerIrisNum.isSuccess()){
	        	return workerIrisNum;
	        }
	        workerInfo.setSId(workerIrisNum.getData());
        	workerInfo.setIrId(workerIrisNum.getData());
        	
        	workerInfo.setSBirth(DateUtil.parse(workerIrisSaveReq.getBirthDate(), "yyyy-MM-dd"));

	        workerInfoMapper.insert(workerInfo);
	        irisInfoMapper.insert(save(workerIrisSaveReq,workerIrisNum.getData()));
	        //特征添加
	        RestResponse<String> addUserCharacteristic = addUserCharacteristic(workerIrisSaveReq,workerInfo.getId());
	        if(!addUserCharacteristic.isSuccess()){//特征添加
				return addUserCharacteristic;

			}
	        return RestResponse.success(workerInfo.getId());
	}
	
	public IrisInfo save(@Valid WorkerIrisSaveReq workerIrisSaveReq,String workerIrisNum) {
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
        irisInfo.setStId(SecurityUtil.getUser().getDeptId());
        irisInfo.setDevType("1.001");
        irisInfo.setCollName(SecurityUtil.getUser().getRealName());
        if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getTemplateL())){
        	irisInfo.setIrisTemplateL(ByteUtil.hexStringToBytes(workerIrisSaveReq.getTemplateL()));
        }
        if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getTemplateR())){
        	irisInfo.setIrisTemplateR(ByteUtil.hexStringToBytes(workerIrisSaveReq.getTemplateR()));
        }
        
        return irisInfo;
}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public 	RestResponse update(CollectUpdateReq collectUpdateReq){
		//获取当前用户id
		String currentUserId = SecurityUtil.getUser().getId();
		//获取人员信息
		WorkerInfo workerInfo = workerInfoMapper.selectById(collectUpdateReq.getId());
		//更新时间
		workerInfo.setUpdateTime(new Date());
		workerInfo.setUpdateUserId(currentUserId);
		workerInfo.setAddr2(collectUpdateReq.getAddr2());
		workerInfo.setSSign(collectUpdateReq.getSSign());
		workerInfo.setFlgFocus(collectUpdateReq.getFlgFocus());
		//当临时关注为 “是” 时，保存临时关注备注和临时关注结束日期
		if("1".equals(collectUpdateReq.getFlgFocus())){
			workerInfo.setFocusDesc(collectUpdateReq.getFocusDesc());
			if(collectUpdateReq.getFocusEndDate() != null){
				workerInfo.setFocusEndDate(DateUtil.DateToString(collectUpdateReq.getFocusEndDate(), 0));
			}
			//当临时关注为 “是” 时，清空临时关注备注和临时关注结束日期
		}else{
			workerInfo.setFocusDesc("");
			workerInfo.setFocusEndDate("");
		}
		//更新人员信息
		workerInfoMapper.updateById(workerInfo);
		//根据 sId 获取 人员虹膜信息
		IrisInfo irisInfo = irisInfoMapper.selectList(new EntityWrapper<IrisInfo>().eq("s_id", workerInfo.getSId())).get(0);
		if (irisInfo != null) {
			//更新时间
			irisInfo.setUpdateTime(new Date());
			irisInfo.setUpdateUserId(currentUserId);
			irisInfo.setTel1(collectUpdateReq.getTel1());
			irisInfo.setTel2(collectUpdateReq.getTel2());
			irisInfo.setReasonColl(collectUpdateReq.getReasonColl());
			irisInfo.setReasonCollDesc(collectUpdateReq.getReasonCollDesc());
			irisInfo.setFlgForce(collectUpdateReq.getFlgForce());
			irisInfo.setFlgWo(collectUpdateReq.getFlgWo());
		}
		//更新人员虹膜信息
		irisInfoMapper.updateById(irisInfo);
		return RestResponse.success();
	}

	public WorkerIrisSaveReq uploadFile(WorkerIrisSaveReq workerIrisSaveReq){
		if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getHeadImage())){//身份证照片
			String headImage = fastDFSTemplate.uploadFile(Base64.getDecoder().decode(workerIrisSaveReq.getHeadImage()),"png");
			workerIrisSaveReq.setHeadImage(fastDFSProperties.getFilePrefix() +headImage);
			logger.debug("*******【文件服务器返回】***********"+headImage);
		}
		if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getIrisL())){//左眼照片
			String irisL = fastDFSTemplate.uploadFile(Base64.getDecoder().decode(workerIrisSaveReq.getIrisL()),"png");
			workerIrisSaveReq.setIrisL(fastDFSProperties.getFilePrefix() +irisL);
		}
		if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getIrisR())){//右眼照片
			String irisR = fastDFSTemplate.uploadFile(Base64.getDecoder().decode(workerIrisSaveReq.getIrisR()),"png");
			workerIrisSaveReq.setIrisR(fastDFSProperties.getFilePrefix() +irisR);
		}
		if(workerIrisSaveReq != null && !StringUtils.isEmpty(workerIrisSaveReq.getIrisFace())){//人脸照片
			String irisFace = fastDFSTemplate.uploadFile(Base64.getDecoder().decode(workerIrisSaveReq.getIrisFace()),"png");
			workerIrisSaveReq.setIrisFace(fastDFSProperties.getFilePrefix() +irisFace);
		}
		return workerIrisSaveReq;
	}

	public RestResponse<String> getWorkerIrisNum (){
		String result = "";
		Dept dept = deptMapper.selectById(SecurityUtil.getUser().getDeptId());
		if (dept == null) {
            return RestResponse.error(ResponseCode.DEPT_NOT_EXISTS, null);
        }
		result = dept.getZzcode() + DateUtil.DateToString(new Date(),0) + "0000000001";
		String codeTemp = workerInfoMapper.getMaxCode(result);
		return RestResponse.success(codeTemp);
	}
	/**
	 * 特征比对
	 * @return
	 */
	public RestResponse<String>  charComparison (WorkerIrisSaveReq workerIrisSaveReq){
		JSONObject json = new JSONObject();
		JSONObject jsonData = new JSONObject();

		jsonData.put("eye_type", workerIrisSaveReq.getIrisType());
		jsonData.put("left", !StringUtils.isEmpty(workerIrisSaveReq.getTemplateL())?workerIrisSaveReq.getTemplateL():"");
		jsonData.put("right", !StringUtils.isEmpty(workerIrisSaveReq.getTemplateR())?workerIrisSaveReq.getTemplateR():"");
		jsonData.put("unknow", "");
		json.put("matcher", jsonData);
		String template = HttpClientUtil.sendPostUrl(irisServerUrl + "/iris/feature/recog",json.toString());
		logger.debug("*******【特征比对接口返回】***********"+template);
		JSONObject parseObject = JSONObject.parseObject(template);
		if(parseObject.containsKey("code") && parseObject.containsKey("info") && parseObject.containsKey("result") && parseObject.containsKey("data")){
			int code = parseObject.getInteger("code");
			String info = parseObject.getString("info");
			int result = parseObject.getInteger("result");
			JSONObject data = (JSONObject) parseObject.get("data");

			if(code != 1 && code != -4001){
				return RestResponse.error(ResponseCode.INTERFACE_ERROR, null);
			}
			if(result != 1 ){
				return RestResponse.error(ResponseCode.RESULT_ERROR, null);
			}
			if(data != null && data.containsKey("userid") &&
					!StringUtils.isEmpty(data.getString("userid"))){
				return RestResponse.error(ResponseCode.IRIS_EXISTS, data.getString("userid"));
			}

		}else{
			return RestResponse.error(ResponseCode.INTERFACE_ERROR, null);
		}

		return RestResponse.success();
	}

	/**
	 * 用户特征添加
	 * @param workerIrisSaveReq
	 * @return
	 */
	public RestResponse<String>  addUserCharacteristic (WorkerIrisSaveReq workerIrisSaveReq,String workerId){
		JSONObject json = new JSONObject();
		JSONObject jsonData = new JSONObject();

		jsonData.put("eye_type", workerIrisSaveReq.getIrisType());
		jsonData.put("left", !StringUtils.isEmpty(workerIrisSaveReq.getTemplateL())?workerIrisSaveReq.getTemplateL():"");
		jsonData.put("right", !StringUtils.isEmpty(workerIrisSaveReq.getTemplateR())?workerIrisSaveReq.getTemplateR():"");
		jsonData.put("unknow", "");

		json.put("feature", jsonData);
		json.put("userid", workerId);
		json.put("is_feature", 1);//特征

		String template = HttpClientUtil.sendPostUrl(irisServerUrl + "/iris/group/users/add",json.toString());
		logger.debug("*******【用户特征添加接口参数】***********"+json.toString());
		logger.debug("*******【用户特征添加接口返回】***********"+template);
		JSONObject parseObject = JSONObject.parseObject(template);
		if(parseObject.containsKey("code") && parseObject.containsKey("info") && parseObject.containsKey("result") && parseObject.containsKey("data")){
			int code = parseObject.getInteger("code");
			String info = parseObject.getString("info");
			int result = parseObject.getInteger("result");
			JSONObject object = (JSONObject) parseObject.get("data");
			if(code != 1 ){
				return RestResponse.error(ResponseCode.INTERFACE_ERROR, null);
			}
			if(result != 1 ){
				return RestResponse.error(ResponseCode.RESULT_ERROR, null);
			}
		}else{
			return RestResponse.error(ResponseCode.INTERFACE_ERROR, null);
		}

		return RestResponse.success();
	}

	@Override
	public RestResponse<WorkerInfo> checkByCerNo(String cerNo) {
		WorkerInfo findByCerNo = workerInfoMapper.findByCerNo(cerNo);
		if(findByCerNo != null){
			return RestResponse.error(ResponseCode.WORKER_NOT_EXISTS, null);
		}
		return  RestResponse.success();
	}

}
