package com.sykean.hmhc.res.iris;

import com.sykean.hmhc.entity.BusIrisRecord;
import lombok.Data;

import java.util.Date;

/**
 * Created by yangsonglin@sykean.com
 * Date 2019/6/24 18:59
 * Description
 */
@Data
public class BusIrisRecordVo extends BusIrisRecord {

    private String codeName;
    private String opTime;
    private String timeColl;
    private String opTeller;
    private String devType;
    private String rsnAlarm;
    private String rsnDesc;
    private String timeJz;

}
