package com.sykean.hmhc.controller;

import javax.validation.Valid;


import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.entity.City;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.res.iris.BusIrisRecordVo;
import com.sykean.hmhc.res.iris.IrisRecord;
import com.sykean.hmhc.res.iris.DistinguishVo;
import com.sykean.hmhc.res.iris.IrisRecordDetail;
import com.sykean.hmhc.res.iris.IrisRecordInfo;
import com.sykean.hmhc.service.CityService;
import io.swagger.annotations.ApiOperation;
import com.sykean.hmhc.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sykean.hmhc.req.disinguish.DistinguishReq;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.service.DistinguishService;

import io.swagger.annotations.Api;

import java.util.List;

/**
 * Created by yangsonglin@sykean.com
 * Date 2019/6/24 9:40
 * Description
 */

@RestController
@RequestMapping("/distinguish")
@Api(value = "识别接口", description = "识别接口")
public class DistinguishController {

    @Autowired
    private DistinguishService distinguishService;

    @ApiOperation("查询识别记录列表")
    @PostMapping("list")
    public RestResponse<PageRes<IrisRecord>> getIrisRecord(@RequestBody @Valid DistinguishReq distinguishReq){
        return distinguishService.getIrisRecordByParamater(distinguishReq);
    }
    @ApiOperation("查询识别记录详情")
    @GetMapping("detail")
    public RestResponse<IrisRecordDetail> getDetail(String id, int pageNo){
        return distinguishService.getIrisRecordDetail(id,pageNo);
    }
    @ApiOperation("查询识别记录历史")
    @GetMapping("recordInfoList")
    public RestResponse<PageRes<IrisRecordInfo>> getRecordInfoList(String irId,int pageNo) {
        return distinguishService.getIrisRecordInfo(irId, pageNo);
    }
    @ApiOperation("识别人员信息")
    @RequestMapping(value="/distinguish",method = {RequestMethod.POST,RequestMethod.GET})
    public RestResponse<DistinguishVo> getDistinguishRecord(@ModelAttribute DistinguishReq distinguishReq){
        User user = SecurityUtil.getUser();
       return distinguishService.getDistinguishRecord(distinguishReq,user);
    }

    @ApiOperation("查询识别记录历史列表")
    @RequestMapping(value="/getBusIrisRecordsList",method = {RequestMethod.POST,RequestMethod.GET})
    public RestResponse<PageInfo<BusIrisRecordVo>> getBusIrisRecordsList(@ModelAttribute DistinguishReq distinguishReq){
        return distinguishService.getBusIrisRecordsList(distinguishReq);
    }

}
