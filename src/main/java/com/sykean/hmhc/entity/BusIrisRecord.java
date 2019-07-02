package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("bus_iris_record")
@Data
public class BusIrisRecord {

  private String id;
  private String ciId;
  private String areaCode;
  private String opType;
  private String flgQuick;
  private String flgMatchToColl;
  private String flgOffline;
  private String flgMarch;
  private String sId;
  private String irId;
  private String stId;
  private String stX;
  private String stY;
  private String dzmc;
  private String chanId;
  private String secLev;
  private String fLst;
  private String levId;
  private String rsnLev;
  private String rsnDetail;
  private String opTeller;
  private String opTime;
  private String timeColl;
  private String timeVerify;
  private String timeJz;
  private String timeSum;
  private String siteType;
  private String devType;
  private String reasonColl;
  private String reasonCollDesc;
  private String collNum;
  private String collName;
  private String flgForce;
  private String flgCer;
  private String flgEye;
  private String flgTypein;
  private String flgFace;
  private String pathFace;
  private String flgAlarm;
  private String flgWo;
  private String flgFocus;
  private String qL;
  private String qR;

  private String createUserId ;
  private Date createTime;
  private String updateUserId;
  private Date updateTime;


}
