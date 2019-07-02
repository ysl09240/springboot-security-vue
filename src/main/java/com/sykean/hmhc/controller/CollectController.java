package com.sykean.hmhc.controller;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sykean.hmhc.req.collect.CollectUpdateReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.WorkerInfo;
import com.sykean.hmhc.req.collect.CollectSearchReq;
import com.sykean.hmhc.req.collect.IriTiquReq;
import com.sykean.hmhc.req.collect.WorkerIrisSaveReq;
import com.sykean.hmhc.res.collect.CollectDetailRes;
import com.sykean.hmhc.res.collect.CollectListRes;
import com.sykean.hmhc.service.CollectService;
import com.sykean.hmhc.util.HttpClientUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;

/**
 * @author litong
 * @description 采集管理
 * @date 2019/6/19
 */
@RestController
@RequestMapping("collect")
@Validated
@Api(value = "采集信息接口", description = "采集信息接口")
public class CollectController {

    @Autowired
    private CollectService collectService;
    @Value("${irisserverurl.irisurl}")
    private String irisServerUrl;
    

    @ApiOperation(value = "采集信息列表")
    @PostMapping("list")
    public RestResponse<PageRes<CollectListRes>> list(@RequestBody @Valid CollectSearchReq collectSearchReq) {
        return collectService.findList(collectSearchReq);
    }

    @ApiOperation(value= "采集信息详情")
    @GetMapping("detail")
    public RestResponse<CollectDetailRes> detail(@RequestParam(value = "id") String id) throws ParseException {
        return collectService.findById(id);
    }
    
    @ApiOperation(value = "新增采集信息")
    @PostMapping("save")
    public RestResponse save(@RequestBody @Valid WorkerIrisSaveReq workerIrisSaveReq) {
        return collectService.save(workerIrisSaveReq);
    }

    @ApiOperation(value = "编辑采集信息")
    @PostMapping("update")
    public RestResponse update(@RequestBody @Valid CollectUpdateReq collectUpdateReq){
        return collectService.update(collectUpdateReq);
    }
    
    @ApiOperation(value = "虹膜特征比对服务")
    //@PostMapping("irisCompariServer")
    @RequestMapping(value = "irisCompariServer", method = RequestMethod.POST)
    public RestResponse irisCompariServer(@RequestBody IriTiquReq iriTiquReq){
		try {
			String str = HttpClientUtil.sendPostUrl(irisServerUrl+iriTiquReq.getUrl(),  iriTiquReq.getData());
			return RestResponse.success(str);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(ResponseCode.IRISSERVER_ERROR, null);
		}
    }
    
    @ApiOperation(value= "校验身份证号是否存在")
    @GetMapping("checkByCerNo")
    public RestResponse<WorkerInfo> checkByCerNo(@RequestParam(value = "cerNo") String cerNo)  {
        return collectService.checkByCerNo(cerNo);
    }

}


