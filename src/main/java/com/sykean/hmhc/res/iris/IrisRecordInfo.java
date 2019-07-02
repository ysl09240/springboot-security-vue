package com.sykean.hmhc.res.iris;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: 魏鑫辉
 * @Date: 2019/6/21 16:04
 * @Description:
 */
@Data
public class IrisRecordInfo {
    @TableId
    private String id;
    //识别站点
    private String dzmc;
    //识别时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opTime;
    //操作人
    private String opTeller;
    //报警原因
    private String alarmReason;
    //报警原因描述
    private String alarmReasonDesc;
    //警综耗时
    private String jzTime;
    //识别设备
    private String devType;


}
