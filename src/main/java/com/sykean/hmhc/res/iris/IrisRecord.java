package com.sykean.hmhc.res.iris;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: 魏鑫辉
 * @Date: 2019/6/19 16:12
 * @Description:
 */
@Data
public class IrisRecord {

    @TableId
    private String id;
    //姓名
    private String name;
    //民族
    private String nation;
    //证件类型
    private String cerType;
    //证件编号
    private String cerNo;
    //户籍地址
    private String residenceAddr;
    //识别时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opTime;
    //识别点位
    private String opAddr;
    //操作人
    private String opWorker;
    //人员类型
    private String personnelType;
    //报警原因
    private String alarmReason;


}
